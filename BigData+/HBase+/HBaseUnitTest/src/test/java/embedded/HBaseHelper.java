package embedded;

import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class HBaseHelper {
    private static final HBaseTestingUtility utility = new HBaseTestingUtility();
    public static final HBaseAdmin admin;
    public static final Connection connection;

    static {
        try {
            HBaseTestingUtility utility = new HBaseTestingUtility();
            utility.startMiniCluster();
            connection = utility.getConnection();
            admin = utility.getHBaseAdmin();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static void stopCluster() {
        try {
            utility.shutdownMiniCluster();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}