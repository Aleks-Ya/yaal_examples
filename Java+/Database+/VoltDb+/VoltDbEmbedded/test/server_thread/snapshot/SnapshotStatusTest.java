package server_thread.snapshot;

import org.junit.jupiter.api.Test;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import server_thread.ServerThreadHelper;

/**
 * Вызов системной процедуры @SnapshotStatus.
 * JVM parameter: -Djava.library.path=libs
 */
class SnapshotStatusTest {

    @Test
    void testServer() throws Exception {
        var port = ServerThreadHelper.runServer();

        Client client = null;
        try {
            client = ClientFactory.createClient();
            client.createConnection("localhost", port);
            var response = client.callProcedure("@SnapshotStatus");
            var table = response.getResults();
            for (var t : table) {
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