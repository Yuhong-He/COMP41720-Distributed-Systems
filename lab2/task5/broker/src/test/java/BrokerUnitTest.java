import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BrokerUnitTest {
    private static Registry registry;
    @BeforeClass
    public static void setup() {
        try {
            registry = LocateRegistry.createRegistry(1099);
            BrokerService localBrokerService = new LocalBrokerService();
            BrokerService brokerService = (BrokerService) UnicastRemoteObject.exportObject(localBrokerService,0);
            registry.bind(Constants.BROKER_SERVICE, brokerService);
        } catch (Exception e) {
            System.out.println("Trouble in BrokerUnitTest: " + e);
        }
    }
    @Test
    public void test1_connectionTest() throws Exception {
        BrokerService service = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
        assertNotNull(service);
    }

    @Test
    public void test2_getQuotations_constantBroker() throws Exception {
        ClientInfo clientInfo = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false);
        BrokerService service = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);
        List<Quotation> quotations = service.getQuotations(clientInfo);
        assertTrue(quotations.isEmpty());
    }
}
