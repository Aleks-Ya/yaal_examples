package two_remote_interfaces;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TwoInterfacesClient {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        Hello helloStub = (Hello) registry.lookup(Hello.BIND_NAME);
        Bye byeStub = (Bye) registry.lookup(Bye.BIND_NAME);

        System.out.println("Hello response: " + helloStub.sayHello());
        System.out.println("Bye response: " + byeStub.sayBye());
    }
}
