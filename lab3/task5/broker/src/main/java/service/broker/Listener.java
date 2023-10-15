package service.broker;

import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;
import java.net.URL;

import static service.broker.LocalBrokerService.urls;

public class Listener implements ServiceListener {

    @Override
    public void serviceAdded(ServiceEvent serviceEvent) {
        System.out.println("Service added: " + serviceEvent.getInfo());
    }

    @Override
    public void serviceRemoved(ServiceEvent serviceEvent) {
        System.out.println("Service removed: " + serviceEvent.getInfo());
    }

    @Override
    public void serviceResolved(ServiceEvent serviceEvent) {
        System.out.println("Service resolved: " + serviceEvent.getInfo());
        String address = serviceEvent.getInfo().getHostAddresses()[0];
        int port = serviceEvent.getInfo().getPort();
        if (address != null && port > 0) {
            String path = "http://" + address + ":" + port + "/quotations?wsdl";
            System.out.println("PATH=" + path);
            try {
                URL newUrl = new URL(path);
                if (!urls.contains(newUrl)) {
                    urls.add(newUrl);
                }
            } catch (Exception e) {
                System.out.println("Problem with service: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
