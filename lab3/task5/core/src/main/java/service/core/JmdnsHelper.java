package service.core;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.net.InetAddress;

public class JmdnsHelper {
    public static void jmdnsAdvertise(int port, String name) {
        try {
            Thread.sleep(1000);
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create("_http._tcp.local.", name, port, "Unused text param");
            System.out.println("serviceInfo of " + name +  ": " + serviceInfo);
            jmdns.registerService(serviceInfo);
            // Wait a bit
            Thread.sleep(100000);
            // Unregister all services
            jmdns.unregisterAllServices();
        } catch (Exception e) {
            System.out.println("Problem Advertising Service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
