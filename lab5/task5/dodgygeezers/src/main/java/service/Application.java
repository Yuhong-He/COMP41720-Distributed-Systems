package service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        String registerDestination = null;
        String serviceUrl = null;

        for (String arg : args) {
            if (arg.startsWith("--registerDestination=")) {
                registerDestination = arg.substring("--registerDestination=".length());
            } else if (arg.startsWith("--serviceUrl=")) {
                serviceUrl = arg.substring("--serviceUrl=".length());
            }
        }

        if (registerDestination == null) {
            registerDestination = "http://localhost:8083/services";
        }
        if (serviceUrl == null) {
            serviceUrl = "http://localhost:8081/quotations";
        }

        try {
            RestTemplate template = new RestTemplate();
            ResponseEntity<String> response = template.postForEntity(registerDestination, serviceUrl, String.class);
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                System.out.println("Service register success");
            } else {
                System.out.println("Service register failed");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("Check the register destination!!!!");
            System.exit(0);
        }
    }
}
