import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        QuotationService afqService = new AFQService();
        try {
            // Connect to the RMI Registry - creating the registry will be the responsibility of the broker.
            Registry registry = LocateRegistry.getRegistry(System.getenv("ADDRESS"), 1099);
            // Create the Remote Object
            QuotationService quotationService = (QuotationService) UnicastRemoteObject.exportObject(afqService,0);
            // Register service into RMI through the proxy XService
            XService xService = (XService) registry.lookup("X");
            xService.registerService(Constants.AULD_FELLAS_SERVICE, quotationService);
            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble in Auldfellas Main: " + e);
        }
    }
}