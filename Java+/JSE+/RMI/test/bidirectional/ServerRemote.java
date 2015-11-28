package bidirectional;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemote extends Remote {
    String BIND_NAME = "server";
    String sayHello() throws RemoteException;
}

