import java.text.NumberFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import org.apache.activemq.ActiveMQConnectionFactory;
import service.core.*;

import javax.jms.*;

public class Main {

	static Map<Long, OfferMessage> cache = new HashMap<>();
	
	/**
	 * This is the starting point for the application. Here, we must
	 * get a reference to the Broker Service and then invoke the
	 * getQuotations() method on that service.
	 * 
	 * Finally, you should print out all quotations returned
	 * by the service.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// create a connection with the Message Broker
			ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
			Connection connection = factory.createConnection();
			connection.setClientID("client");
			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

			// connect the client to myBroker
			Queue queueClientRequest = session.createQueue("REQUEST");
			Queue queueClientResponses = session.createQueue("RESPONSES");
			MessageProducer producerToBroker = session.createProducer(queueClientRequest);
			MessageConsumer consumerToBroker = session.createConsumer(queueClientResponses);

			connection.start();

			for (ClientInfo info : clients) {
				ClientMessage clientMessage = new ClientMessage(UUID.randomUUID().hashCode(), info);
				Message request = session.createObjectMessage(clientMessage);
				// add the ID and client as an object to the cache
				cache.put(clientMessage.getToken(), new OfferMessage(clientMessage.getInfo(), new LinkedList<>()));
				producerToBroker.send(request);
			}

			new Thread(() -> {
				while (true) {
					try {
						Message message = consumerToBroker.receive();
						OfferMessage offerMessage = (OfferMessage) ((ObjectMessage) message).getObject();
						ClientInfo info = offerMessage.getInfo();
						displayProfile(info);
						for (Quotation quote : offerMessage.getQuotations()) {
							displayQuotation(quote);
						}
						System.out.println("\n");
						message.acknowledge();
					} catch (JMSException e) {
						System.out.println("JMSException in Broker Main to Service: " + e);
					}
				}
			}).start();

		} catch (JMSException e) {
			System.out.println("JMSException in Client Main: " + e);
		}
	}
	
	/**
	 * Display the client info nicely.
	 * 
	 * @param info
	 */
	public static void displayProfile(ClientInfo info) {
		System.out.println("|=================================================================================================================|");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println(
				"| Name: " + String.format("%1$-29s", info.name) + 
				" | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
				" | Age: " + String.format("%1$-30s", info.age)+" |");
		System.out.println(
				"| Weight/Height: " + String.format("%1$-20s", info.weight+"kg/"+info.height+"m") + 
				" | Smoker: " + String.format("%1$-27s", info.smoker?"YES":"NO") +
				" | Medical Problems: " + String.format("%1$-17s", info.medicalIssues?"YES":"NO")+" |");
		System.out.println("|                                     |                                     |                                     |");
		System.out.println("|=================================================================================================================|");
	}

	/**
	 * Display a quotation nicely - note that the assumption is that the quotation will follow
	 * immediately after the profile (so the top of the quotation box is missing).
	 * 
	 * @param quotation
	 */
	public static void displayQuotation(Quotation quotation) {
		System.out.println(
				"| Company: " + String.format("%1$-26s", quotation.company) + 
				" | Reference: " + String.format("%1$-24s", quotation.reference) +
				" | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
		System.out.println("|=================================================================================================================|");
	}
	
	/**
	 * Test Data
	 */
	public static final ClientInfo[] clients = {
		new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false),
		new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 1.6, 100, true, true),
		new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 21, 1.78, 65, false, false),
		new ClientInfo("Rem Collier", ClientInfo.MALE, 49, 1.8, 120, false, true),
		new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 1.9, 75, true, false),
		new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 0.45, 1.6, false, false)
	};
}
