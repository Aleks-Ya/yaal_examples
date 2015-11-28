package two_remote_interfaces;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    String BIND_NAME = "Hello";
    String sayHello() throws RemoteException;
}

