package two_remote_interfaces;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Bye extends Remote {
    String BIND_NAME = "Bye";
    String sayBye() throws RemoteException;
}

