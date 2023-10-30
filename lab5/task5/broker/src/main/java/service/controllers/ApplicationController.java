package service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.core.Application;
import service.core.ClientInfo;
import service.core.Quotation;

import java.net.URI;
import java.util.*;

@RestController
public class ApplicationController {

    private final Map<Integer, Application> applications = new TreeMap<>();
    private final List<String> serviceUrls = new ArrayList<>();

    @PostMapping(value="/applications", consumes="application/json")
    public ResponseEntity<Application> createQuotation(
            @RequestBody ClientInfo info) {

        Application application = new Application(info);

        for(String url: serviceUrls) {
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

    @PostMapping(value="/services", consumes="text/plain")
    public ResponseEntity<Application> registerService(@RequestBody String url) {
        if (!serviceUrls.contains(url)) {
            serviceUrls.add(url);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            System.out.println("The service URL already registered");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping(value="/services", produces={"application/json"})
    public ResponseEntity<List<String>> getServices() {
        return ResponseEntity.status(HttpStatus.OK).body(serviceUrls);
    }
}
