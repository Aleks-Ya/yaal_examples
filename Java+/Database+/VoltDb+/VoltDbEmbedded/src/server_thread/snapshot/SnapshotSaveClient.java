package server_thread.snapshot;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcCallException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

/**
 * Сохранение снимка БД с помощью системной процедуры @SnapshotSave.
 */
public class SnapshotSaveClient {
    public static void runClient(int port) throws IOException, ProcCallException {
        Client client = null;
        try {
            client = ClientFactory.createClient();
            client.createConnection("localhost", port);
            String snapshotDir = Files.createTempDirectory("SnapshotSaveClient_").toString();
            System.out.println("Snapshot save dir: " + snapshotDir);
            String uniqueId = String.valueOf(new Date().getTime());
            int blockOtherTransaction = 1;
            ClientResponse response = client.callProcedure("@SnapshotSave", snapshotDir, uniqueId, blockOtherTransaction);
            VoltTable[] table = response.getResults();
            for (VoltTable t : table) {
                System.out.println(t.toString());
            }

            System.out.println("Scan results:");
            ClientResponse scanResponse = client.callProcedure("@SnapshotScan", snapshotDir);
            VoltTable[] scanResults = scanResponse.getResults();
            for (VoltTable t : scanResults) {
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