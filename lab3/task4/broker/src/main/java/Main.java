import service.broker.LocalBrokerService;

import javax.xml.ws.Endpoint;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        List<URL> urls = new LinkedList<>();
        if(args.length == 1 && args[0].equals("-Docker")) { // running in docker
            System.out.println("Running broker in Docker...");
            System.out.println("Quotation URLs are:");
            String quotation_service_urls = System.getenv("URLS");
            if(quotation_service_urls != null) {
                String[] urlStrings = quotation_service_urls.split(",");
                for (String urlString : urlStrings) {
                    if (isValidURL(urlString.trim())) {
                        System.out.println("URL: " + urlString);
                        URL url = new URL(urlString.trim());
                        urls.add(url);
                    }
                }
            } else {
                System.out.println("No URL");
                System.exit(0);
            }
        } else { // running in localhost
            System.out.println("Running broker in Localhost...");
            System.out.println("Quotation URLs are:");
            if(args.length != 0) {
                for(String arg: args) {
                    if (isValidURL(arg.trim())) {
                        System.out.println("URL: " + arg);
                        URL url = new URL(arg.trim());
                        urls.add(url);
                    }
                }
            } else {
                System.out.println("No URL");
                System.exit(0);
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
