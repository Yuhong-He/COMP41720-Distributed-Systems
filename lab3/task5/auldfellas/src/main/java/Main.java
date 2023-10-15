import service.auldfellas.AFQService;

import javax.xml.ws.Endpoint;

import static service.core.JmdnsHelper.jmdnsAdvertise;

public class Main {
    public static void main(String[] args) {
        String host = "0.0.0.0";
        int port = 9001;
        Endpoint.publish("http://" + host + ":" + port + "/quotations", new AFQService());
        System.out.println("http://" + host + ":" + port + "/quotations?wsdl");

        jmdnsAdvertise(port, "afq-service");
    }

}
