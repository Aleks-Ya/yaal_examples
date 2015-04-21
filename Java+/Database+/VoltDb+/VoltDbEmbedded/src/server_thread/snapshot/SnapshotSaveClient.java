package server_thread.snapshot;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientFactory;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcCallException;
import system_procedure_wrapper.SnapshotScanWrapper;

import java.io.File;
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
            File snapshotDir = Files.createTempDirectory("SnapshotSaveClient_").toFile();
            System.out.println("Snapshot save dir: " + snapshotDir);
            String uniqueId = String.valueOf(new Date().getTime());
            int blockOtherTransaction = 1;
            ClientResponse response = client.callProcedure("@SnapshotSave", snapshotDir.getAbsolutePath(), uniqueId, blockOtherTransaction);
            VoltTable[] table = response.getResults();
            for (VoltTable t : table) {
                System.out.println(t.toString());
            }

            System.out.println("Scan results:");
            SnapshotScanWrapper scan = new SnapshotScanWrapper(client, snapshotDir);
            for (SnapshotScanWrapper.SnapshotInfo snapshot : scan.getSnapshots()) {
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
    }
}