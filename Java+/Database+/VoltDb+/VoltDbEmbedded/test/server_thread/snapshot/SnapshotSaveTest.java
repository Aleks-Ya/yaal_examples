package server_thread.snapshot;

import org.junit.jupiter.api.Test;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import server_thread.ServerThreadHelper;
import system_procedure_wrapper.SnapshotScanWrapper;

import java.nio.file.Files;

/**
 * Сохранение снимка БД с помощью системной процедуры @SnapshotSave.
 * JVM parameter: -Djava.library.path=libs
 */
class SnapshotSaveTest {

    @Test
    void testServer() throws Exception {
        var port = ServerThreadHelper.runServer();

        Client client = null;
        try {
            client = ClientFactory.createClient();
            client.createConnection("localhost", port);
            var snapshotDir = Files.createTempDirectory("SnapshotSaveClient_").toFile();
            System.out.println("Snapshot save dir: " + snapshotDir);
            var uniqueId = "my_snapshot_id";
            var blockOtherTransaction = 1;
            var response = client.callProcedure("@SnapshotSave", snapshotDir.getAbsolutePath(), uniqueId, blockOtherTransaction);
            var table = response.getResults();
            for (var t : table) {
                System.out.println(t.toString());
            }

            System.out.println("Scan results:");
            var scan = new SnapshotScanWrapper(client, snapshotDir);
            for (var snapshot : scan.getSnapshots()) {
                System.out.println(snapshot);
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