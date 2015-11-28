package bidirectional.subscription;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ObserverServer {


    public static void main(String args[]) throws RemoteException, AlreadyBoundException, NotBoundException {
        ServerRemote serverRemote = new ServerRemoteImpl();
//        ServerRemote serverRemoteStub = (ServerRemote) UnicastRemoteObject.exportObject(serverRemote, 0);

        Registry registry = LocateRegistry.createRegistry(ServerRemote.REGISTRY_PORT);
        registry.bind(ServerRemote.BIND_NAME, serverRemote);

        System.out.println("Registry: " + registry);
    }

    public static class ServerRemoteImpl extends UnicastRemoteObject implements ServerRemote {
        private final List<Subscriber> subscribers = new ArrayList<>();

        protected ServerRemoteImpl() throws RemoteException {
        }

        @Override
        public void subscribe(Subscriber subscriber) throws RemoteException {
            System.out.println("Add subscriber: " + subscriber);
            subscribers.add(subscriber);
        }

        @Override
        public boolean unSubscribe(Subscriber subscriber) throws RemoteException {
            System.out.println("Remove subscriber: " + subscriber);
            return subscribers.remove(subscriber);
        }

        @Override
        public void sendTestData(Data data) throws RemoteException {
            System.out.println("Send test data: " + data);
            subscribers.stream().forEach(subscriber -> {
                try {
                    subscriber.process(data);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}