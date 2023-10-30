package service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.core.Application;
import service.core.ClientInfo;
import service.core.Quotation;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class ApplicationController {

    private final Map<Integer, Application> applications = new TreeMap<>();

    @PostMapping(value="/applications", consumes="application/json")
    public ResponseEntity<Application> createQuotation(
            @RequestBody ClientInfo info,
            @RequestParam("urls") String[] urls) {

        Application application = new Application(info);

        for(String url: urls) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            if(isValidURL(url)) {
                RestTemplate template = new RestTemplate();
                ResponseEntity<String> response = template.postForEntity(url, info, String.class);
                if (response.getStatusCode().equals(HttpStatus.CREATED)) {
                    URI location = response.getHeaders().getLocation();
                    if(location != null) {
                        Quotation quotation = template.getForObject(location, Quotation.class);
                        if(quotation != null) {
                            application.quotations.add(quotation);
                            applications.put(application.id, application);
                        } else {
                            System.out.println("Convert response body to quotation failed: Body is null");
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                        }
                    } else {
                        System.out.println("Service didn't return location in response header");
                        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
                    }
                } else {
                    System.out.println("Post Application error: Created failed");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            } else {
                System.out.println("Invalid URL: " + url);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(application);
    }

    @GetMapping(value="/applications/{id}", produces={"application/json"})
    public ResponseEntity<Application> getQuotation(@PathVariable String id) {
        Application application = applications.get(Integer.parseInt(id));
        if (application == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(application);
    }

    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
