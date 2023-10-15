import org.junit.BeforeClass;
import org.junit.Test;
import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class QuotationServiceUnitTest {

    @BeforeClass
    public static void setup() throws MalformedURLException {
        List<URL> urls = new LinkedList<>();
        urls.add(new URL("http://0.0.0.0:9001/quotations"));
        urls.add(new URL("http://0.0.0.0:9002/quotations"));
        urls.add(new URL("http://0.0.0.0:9003/quotations"));
//        Endpoint.publish("http://0.0.0.0:9000/quotations", new LocalBrokerService(urls));
    }

    @Test
    public void connectionTest() throws Exception {
        URL wsdlUrl = new URL("http://localhost:9000/quotations?wsdl");
        QName serviceName = new QName("http://core.service/", "QuotationService");
        Service service = Service.create(wsdlUrl, serviceName);
        QName portName = new QName("http://core.service/", "QuotationServicePort");
        BrokerService brokerService = service.getPort(portName, BrokerService.class);
        List<Quotation> quotations = brokerService.getQuotations(new ClientInfo(
                        "Niki Collier", ClientInfo.FEMALE, 49,
                        1.5494, 80, false, false));
        assertEquals(3, quotations.size());
        assertEquals("Auld Fellas Ltd.", quotations.get(0).company);
        assertEquals("Dodgy Geezers Corp.", quotations.get(1).company);
        assertEquals("Girls Allowed Inc.", quotations.get(2).company);
    }

}
