package bidirectional;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BidirectionalServer {

    static Registry registry;

    public static void main(String args[]) throws RemoteException, AlreadyBoundException, NotBoundException {
        ServerRemote serverRemote = new ServerRemoteImpl();
        ServerRemote serverRemoteStub = (ServerRemote) UnicastRemoteObject.exportObject(serverRemote, 0);

        registry = LocateRegistry.createRegistry(1099);
        registry.bind(ServerRemote.BIND_NAME, serverRemoteStub);

        System.out.println("Registry: " + registry);
        System.err.println("Server ready");

        //shutdown server
//        UnicastRemoteObject.unexportObject(obj, false);
    }

    public static class ServerRemoteImpl implements ServerRemote {
        @Override
        public String sayHello() throws RemoteException {
            try {
                ClientRemote clientRemote = (ClientRemote) registry.lookup(ClientRemote.BIND_NAME);
                System.out.println("Client response: " + clientRemote.sayHello());
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            return "Hello from ServerRemote";
        }
    }
}