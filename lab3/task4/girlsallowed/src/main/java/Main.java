import service.girlsallowed.GAQService;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        String host = "localhost:9003";
        String env_host = System.getenv("HOST");
        if(env_host != null) {
            host = env_host;
        }
        Endpoint.publish("http://" + host + "/quotations", new GAQService());
        System.out.println("http://" + host + "/quotations?wsdl");
    }
}
