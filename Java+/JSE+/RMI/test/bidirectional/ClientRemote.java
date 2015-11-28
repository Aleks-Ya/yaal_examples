package bidirectional;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRemote extends Remote {
    String BIND_NAME = "client";
    String sayHello() throws RemoteException;
}

