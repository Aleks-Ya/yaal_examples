package server_thread.snapshot;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcCallException;

import java.io.IOException;

/**
 * Вызов системной процедуры @SnapshotStatus.
 */
public class SnapshotStatusClient {
    public static void runClient(int port) throws IOException, ProcCallException {
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
    }
}