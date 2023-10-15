import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.assertNotNull;

public class DodgygeezersUnitTest {
    private static Registry registry;
    @BeforeClass
    public static void setup() {
        QuotationService dgqService = new DGQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(dgqService,0);
            registry.bind(Constants.DODGY_GEEZERS_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.DODGY_GEEZERS_SERVICE);
        assertNotNull(service);
    }

    @Test
    public void generateQuotation() throws Exception {
        ClientInfo clientInfo = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false);
        QuotationService service = (QuotationService) registry.lookup(Constants.DODGY_GEEZERS_SERVICE);
        Quotation quotation = service.generateQuotation(clientInfo);
        assertNotNull(quotation);
    }
}
