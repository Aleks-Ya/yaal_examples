package two_remote_interfaces;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TwoInterfacesServer {


    public static void main(String args[]) throws RemoteException, AlreadyBoundException, NotBoundException {
        Hello hello = new HelloImpl();
        Bye bye = new ByeImpl();

        Hello helloStub = (Hello) UnicastRemoteObject.exportObject(hello, 0);
        Bye byeStub = (Bye) UnicastRemoteObject.exportObject(bye, 0);

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind(Hello.BIND_NAME, helloStub);
        registry.bind(Bye.BIND_NAME, byeStub);

        System.out.println("Registry: " + registry);
        System.err.println("Server ready");

        //shutdown server
//        UnicastRemoteObject.unexportObject(obj, false);
    }

    public static class HelloImpl implements Hello {
        @Override
        public String sayHello() throws RemoteException {
                return "Hello, world!";
        }
    }

    public static class ByeImpl implements Bye {
        @Override
        public String sayBye() throws RemoteException {
                return "Good bye, world!";
        }
    }
}