import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class XObject implements XService {
    @Override
    public void registerService(String serviceName, Remote service) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        try {
            registry.bind(serviceName, service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
