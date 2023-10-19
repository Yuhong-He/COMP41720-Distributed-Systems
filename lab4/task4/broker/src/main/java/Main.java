import org.apache.activemq.ActiveMQConnectionFactory;
import service.core.ClientMessage;
import service.core.OfferMessage;
import service.core.QuotationMessage;

import javax.jms.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {
    static Map<Long, OfferMessage> cache = new HashMap<>();

    public static void main(String[] args) {
        try {
            // create a connection with the Message Broker
            ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
            Connection connection = factory.createConnection();
            connection.setClientID("myBroker");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // connect the broker to the client
            Queue queueClientRequest = session.createQueue("REQUEST");
            Queue queueClientResponses = session.createQueue("RESPONSES");
            MessageConsumer consumerToClient = session.createConsumer(queueClientRequest);
            MessageProducer producerToClient = session.createProducer(queueClientResponses);

            // connect the broker to ActiveMQ client (services)
            Queue queueServiceQuotations = session.createQueue("QUOTATIONS");
            Topic topicServiceTopic = session.createTopic("APPLICATIONS");
            MessageConsumer consumerToService = session.createConsumer(queueServiceQuotations);
            MessageProducer producerToService = session.createProducer(topicServiceTopic);

            connection.start();

            // handle connection with client
            new Thread(() -> {
                while (true) {
                    try {
                        Message message = consumerToClient.receive();
                        ClientMessage clientMessage = (ClientMessage) ((ObjectMessage) message).getObject();
                        if(!cache.containsKey(clientMessage.getToken())) {
                            cache.put(clientMessage.getToken(), new OfferMessage(clientMessage.getInfo(), new LinkedList<>()));
                            Message request = session.createObjectMessage(clientMessage);
                            producerToService.send(request);
                        }

                        Thread.sleep(1000);

                        OfferMessage completedApplication = cache.get(clientMessage.getToken());
                        Message quotationSet = session.createObjectMessage(completedApplication);
                        producerToClient.send(quotationSet);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JMSException e) {
                        System.out.println("JMSException in Broker Main to Client: " + e);
                    }
                }
            }).start();

            // handle connection with services
            new Thread(() -> {
                while (true) {
                    try {
                        Message message = consumerToService.receive();
                        QuotationMessage quotationMessage = (QuotationMessage) ((ObjectMessage) message).getObject();
                        if(cache.containsKey(quotationMessage.getToken())) {
                            OfferMessage offerMessage = cache.get(quotationMessage.getToken());
                            offerMessage.getQuotations().add(quotationMessage.getQuotation());
                        }
                    } catch (JMSException e) {
                        System.out.println("JMSException in Broker Main to Service: " + e);
                    }
                }
            }).start();

        } catch (JMSException e) {
            System.out.println("JMSException in Broker Main: " + e);
        }
    }
}
