import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static org.junit.Assert.assertNotNull;

public class GirlsallowedUnitTest {
    private static Registry registry;
    @BeforeClass
    public static void setup() {
        QuotationService gaqService = new GAQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(gaqService,0);
            registry.bind(Constants.GIRLS_ALLOWED_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }

    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.GIRLS_ALLOWED_SERVICE);
        assertNotNull(service);
    }

    @Test
    public void generateQuotation() throws Exception {
        ClientInfo clientInfo = new ClientInfo("Niki Collier", ClientInfo.FEMALE, 49, 1.5494, 80, false, false);
        QuotationService service = (QuotationService) registry.lookup(Constants.GIRLS_ALLOWED_SERVICE);
        Quotation quotation = service.generateQuotation(clientInfo);
        assertNotNull(quotation);
    }
}
