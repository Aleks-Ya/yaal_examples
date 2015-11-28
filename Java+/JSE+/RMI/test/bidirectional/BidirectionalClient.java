package bidirectional;


import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BidirectionalClient {

    public static void main(String[] args) throws RemoteException, NotBoundException, AlreadyBoundException {
        ClientRemote clientRemote = new ClientRemoteImpl();
        ClientRemote clientStub = (ClientRemote) UnicastRemoteObject.exportObject(clientRemote, 0);

        Registry registry = LocateRegistry.getRegistry();
        registry.bind(ClientRemote.BIND_NAME, clientStub);

        ServerRemote serverRemote = (ServerRemote) registry.lookup(ServerRemote.BIND_NAME);
        System.out.println("Server response: " + serverRemote.sayHello());
    }

    public static class ClientRemoteImpl implements ClientRemote {
        @Override
        public String sayHello() throws RemoteException {
            return "Hello from ClientRemote";
        }
    }
}
