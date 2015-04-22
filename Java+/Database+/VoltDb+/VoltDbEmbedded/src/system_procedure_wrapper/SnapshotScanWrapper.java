package system_procedure_wrapper;

import org.voltdb.VoltTable;
import org.voltdb.client.Client;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcCallException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Обертка для удобного парсинга результата вызова хранимой процедуры @SnapshotScan.
 */
public class SnapshotScanWrapper {
    private List<SnapshotInfo> snapshots = new ArrayList<>();
    private List<HostInfo> hosts = new ArrayList<>();
    private List<FileInfo> files = new ArrayList<>();

    public SnapshotScanWrapper(Client client, File snapshotDir) throws IOException, ProcCallException {
        ClientResponse scanResponse = client.callProcedure("@SnapshotScan", snapshotDir.getAbsolutePath());
        VoltTable[] scanResults = scanResponse.getResults();
        VoltTable snapshotsTable = scanResults[0];
        snapshotsTable.advanceRow();
        for (int r = 0; r < snapshotsTable.getRowCount(); r++) {
            snapshots.add(new SnapshotInfo(
                    snapshotsTable.getString(0),
                    snapshotsTable.getString(1),
                    snapshotsTable.getLong(2),
                    snapshotsTable.getLong(3),
                    snapshotsTable.getLong(4),
                    snapshotsTable.getString(5),
                    snapshotsTable.getString(6),
                    snapshotsTable.getString(7),
                    snapshotsTable.getString(8)
            ));
        }
    }

    public List<SnapshotInfo> getSnapshots() {
        return snapshots;
    }

    public List<HostInfo> getHosts() {
        return hosts;
    }

    public List<FileInfo> getFiles() {
        return files;
    }

    public static class SnapshotInfo {
        private String path;
        private String nonce;
        private long transactionId;
        private long created;
        private long size;
        private List<String> tablesRequered;
        private List<String> tablesMissing;
        private List<String> tablesIncomplete;
        private boolean complete;

        public SnapshotInfo(String path, String nonce, long transactionId, long created, long size,
                            String tablesRequered, String tablesMissing, String tablesIncomplete,
                            String complete) {
            this.path = path;
            this.nonce = nonce;
            this.transactionId = transactionId;
            this.created = created;
            this.size = size;
            this.tablesRequered = parseCsvString(tablesRequered);
            this.tablesMissing = parseCsvString(tablesMissing);
            this.tablesIncomplete = parseCsvString(tablesIncomplete);
            this.complete = Boolean.parseBoolean(complete);
        }

        private List<String> parseCsvString(String str) {
            return Arrays.asList(str.split(";"));
        }

        @Override
        public String toString() {
            return "SnapshotInfo{" +
                    "path='" + path + '\'' +
                    ", nonce='" + nonce + '\'' +
                    ", transactionId=" + transactionId +
                    ", created=" + created +
                    ", size=" + size +
                    ", tablesRequered=" + tablesRequered +
                    ", tablesMissing=" + tablesMissing +
                    ", tablesIncomplete=" + tablesIncomplete +
                    ", complete=" + complete +
                    '}';
        }
    }

    public static class HostInfo {

    }

    public static class FileInfo {

    }

}
