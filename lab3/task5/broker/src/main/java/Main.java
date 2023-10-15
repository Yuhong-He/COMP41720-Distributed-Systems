import service.broker.Listener;
import service.broker.LocalBrokerService;

import javax.jmdns.JmDNS;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        String host = "0.0.0.0";
        Endpoint.publish("http://" + host + ":9000/quotations", new LocalBrokerService());
        System.out.println("http://" + host + ":9000/quotations?wsdl");

        JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
        jmdns.addServiceListener("_http._tcp.local.", new Listener());
    }
}
