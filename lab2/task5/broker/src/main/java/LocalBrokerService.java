import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
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

	@Override
	public List<Quotation> getQuotations(ClientInfo info) throws RemoteException {
		List<Quotation> quotations = new LinkedList<Quotation>();
		Registry registry = LocateRegistry.getRegistry("localhost", 1099);
		try {
			for (String name : registry.list()) {
				if (name.startsWith("qs-")) {
					QuotationService service = (QuotationService) registry.lookup(name);
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
