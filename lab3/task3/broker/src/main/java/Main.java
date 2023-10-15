import service.broker.LocalBrokerService;

import javax.xml.ws.Endpoint;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        List<URL> urls = new LinkedList<>();
        for(String str: args) {
            if(isValidURL(str)) {
                URL url = new URL(str);
                urls.add(url);
            }
        }
        Endpoint.publish("http://0.0.0.0:9000/quotations", new LocalBrokerService(urls));
        System.out.println("http://localhost:9000/quotations?wsdl");
    }

    public static boolean isValidURL(String str) {
        try {
            new URL(str).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
