package bidirectional.subscription;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemote extends Remote {
    String BIND_NAME = "server";
    int REGISTRY_PORT = 1099;

    void subscribe(Subscriber subscriber) throws RemoteException;
    boolean unSubscribe(Subscriber subscriber) throws RemoteException;

    void sendTestData(Data data) throws RemoteException;
}

