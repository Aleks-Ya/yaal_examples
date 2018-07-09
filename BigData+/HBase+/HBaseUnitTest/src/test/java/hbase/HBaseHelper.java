package hbase;

import org.apache.hadoop.hbase.HBaseTestingUtility;

public class HBaseHelper {
    public static final HBaseTestingUtility utility = new HBaseTestingUtility();

    static {
        try {
            utility.startMiniCluster();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }
}