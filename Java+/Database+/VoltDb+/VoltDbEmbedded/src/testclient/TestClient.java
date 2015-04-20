package testclient;

import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;

public class TestClient {
    public static void runClient(int port) {
        Client client = null;
        try {
            client = ClientFactory.createClient();
            client.createConnection("localhost", port);

            // Perform the client actions.
            client.callProcedure("TestProcedure", 1, 111, "some text");
            // More actions go here.
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}