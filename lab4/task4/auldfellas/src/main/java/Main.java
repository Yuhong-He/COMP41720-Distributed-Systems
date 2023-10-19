import org.apache.activemq.ActiveMQConnectionFactory;
import service.auldfellas.AFQService;
import service.core.ClientMessage;
import service.core.Quotation;
import service.core.QuotationMessage;

import javax.jms.*;

public class Main {
    private static AFQService service = new AFQService();

    public static void main(String[] args) {
        try {
            // create a connection with the Message Broker
            ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
            Connection connection = factory.createConnection();
            connection.setClientID("auldfellas");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            // connect the ActiveMQ client to the relevant queues (QUOTATIONS) and topics (APPLICATIONS)
            Queue queue = session.createQueue("QUOTATIONS");
            Topic topic = session.createTopic("APPLICATIONS");
            MessageConsumer consumer = session.createConsumer(topic);
            MessageProducer producer = session.createProducer(queue);

            connection.start();
            // makes the service consume incoming ClientMessage message,
            // generating a Quotation and then producing a QuotationMessage message that it submits to the QUOTATIONS queue
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        ClientMessage request = (ClientMessage) ((ObjectMessage) message).getObject();
                        Quotation quotation = service.generateQuotation(request.getInfo());
                        Message response = session.createObjectMessage(new QuotationMessage(request.getToken(), quotation));
                        producer.send(response);
                        message.acknowledge();
                    } catch (JMSException e) {
                        System.out.println("JMSException in AFQService Main consumer.setMessageListener: " + e);
                    }
                }
            });
        } catch (JMSException e) {
            System.out.println("JMSException in AFQService Main: " + e);
        }
    }
}
