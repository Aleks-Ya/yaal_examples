package bidirectional.subscription;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ObserverClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, AlreadyBoundException {
        Subscriber subscriber = new ClientRemoteImpl();
        Registry registry = LocateRegistry.getRegistry(ServerRemote.REGISTRY_PORT);
        ServerRemote serverRemote = (ServerRemote) registry.lookup(ServerRemote.BIND_NAME);

        serverRemote.subscribe(subscriber);
        serverRemote.sendTestData(new Data("Test data 2"));
        System.out.println("Un subscribe: " + serverRemote.unSubscribe(subscriber));
    }

    public static class ClientRemoteImpl extends UnicastRemoteObject implements Subscriber {
        protected ClientRemoteImpl() throws RemoteException {
        }

        @Override
        public void process(Data data) throws RemoteException {
            System.out.println("Process data: " + data);
        }
    }
}
