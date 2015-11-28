package bidirectional.subscription;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Subscriber extends Remote {
    String BIND_NAME = "subscriber";

    void process(Data data) throws RemoteException;
}
