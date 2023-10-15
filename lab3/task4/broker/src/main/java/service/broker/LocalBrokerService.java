package service.broker;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.core.QuotationService;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * Implementation of the broker service that uses the Service Registry.
 * 
 * @author Rem
 *
 */
@WebService(name="QuotationService",
		targetNamespace="http://core.service/",
		serviceName="QuotationService")
@SOAPBinding(style= SOAPBinding.Style.DOCUMENT, use= SOAPBinding.Use.LITERAL)
public class LocalBrokerService implements BrokerService {

	static List<URL> urls = new LinkedList<>();

	@SuppressWarnings("unused")
	public LocalBrokerService() { // no params constructor use to solve
	}

	public LocalBrokerService(List<URL> urls) {
		LocalBrokerService.urls = urls;
	}

	@WebMethod
	@Override
	public List<Quotation> getQuotations(ClientInfo info) {
		List<Quotation> quotations = new LinkedList<>();
		
		for (URL url : urls) {
			QName serviceName = new QName("http://core.service/", "QuotationService");
			QName portName = new QName("http://core.service/", "QuotationServicePort");
			Service service = Service.create(url, serviceName);
			QuotationService quotationService = service.getPort(portName, QuotationService.class);
			quotations.add(quotationService.generateQuotation(info));
		}

		return quotations;
	}
}
