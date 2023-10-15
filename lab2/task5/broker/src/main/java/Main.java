import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        try {
            // Connect to the RMI Registry - creating the registry will be the responsibility of the broker.
            Registry registry = LocateRegistry.createRegistry(1099);
            // Create LocalBrokerService Object
            LocalBrokerService localBrokerService = new LocalBrokerService();
            // Create the Remote Object
            BrokerService brokerService = (BrokerService) UnicastRemoteObject.exportObject(localBrokerService,0);
            // Register the object with the RMI Registry
            registry.bind(Constants.BROKER_SERVICE, brokerService);

            // Create proxy
            XObject x = new XObject();
            XService xService = (XService) UnicastRemoteObject.exportObject(x,0);
            registry.bind("X", xService);

            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble in Broker Main: " + e);
        }
    }

}
