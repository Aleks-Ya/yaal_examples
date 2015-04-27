package server_thread.snapshot;

import org.junit.Test;
import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import server_thread.ServerThreadHelper;

/**
 * Вызов системной процедуры @SnapshotStatus.
 * JVM parameter: -Djava.library.path=libs
 */
public class SnapshotStatusTest {

    @Test
    public void testServer() throws Exception {
        int port = ServerThreadHelper.runServer();

        Client client = null;
        try {
            client = ClientFactory.createClient();
            client.createConnection("localhost", port);
            ClientResponse response = client.callProcedure("@SnapshotStatus");
            VoltTable[] table = response.getResults();
            for (VoltTable t : table) {
                System.out.println(t.toString());
            }
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ServerThreadHelper.stopServer();
    }
}