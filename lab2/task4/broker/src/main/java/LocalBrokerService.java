import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
public class LocalBrokerService implements BrokerService {

	Registry service_registry;

	public LocalBrokerService(Registry service_registry) {
		this.service_registry = service_registry;
	}

	@Override
	public List<Quotation> getQuotations(ClientInfo info) {
		List<Quotation> quotations = new LinkedList<Quotation>();
		try {
			for (String name : service_registry.list()) {
				if (name.startsWith("qs-")) {
					QuotationService service = (QuotationService) service_registry.lookup(name);
					quotations.add(service.generateQuotation(info));
				}
			}
			return quotations;
		} catch (Exception e) {
			System.out.println("Trouble in LocalBrokerService: " + e);
			return null;
		}
	}
}
