import java.rmi.Remote;
import java.rmi.RemoteException;

public interface XService extends Remote {
    public void registerService(String serviceName, Remote service) throws RemoteException;
}
