package phoenix.factory;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.hadoop.hbase.MiniHBaseCluster;
import org.apache.hadoop.hbase.zookeeper.MiniZooKeeperCluster;
import org.apache.phoenix.jdbc.PhoenixTestDriver;
import org.apache.phoenix.query.BaseTest;
import org.apache.phoenix.query.QueryServices;
import org.apache.phoenix.util.ReadOnlyProps;

import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL;
import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL_SEPARATOR;
import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL_TERMINATOR;
import static org.apache.phoenix.util.PhoenixRuntime.PHOENIX_TEST_DRIVER_URL_PARAM;
import static org.apache.phoenix.util.TestUtil.LOCALHOST;

@SuppressWarnings({"WeakerAccess", "unused"})
public class PhoenixFactory {

    private final HBaseTestingUtility hbaseTestingUtility;
    private final PhoenixTestDriver driver;
    private final String url;
    private final MiniZooKeeperCluster zooKeeperCluster;
    private final MiniHBaseCluster hBaseCluster;

    public PhoenixFactory() {
        this(2181);
    }

    public PhoenixFactory(int zooKeeperClientPort) {
        try {
            Configuration conf = HBaseConfiguration.create();
            BaseTest.setUpConfigForMiniCluster(conf);
            conf.setInt(QueryServices.ZOOKEEPER_PORT_ATTRIB, zooKeeperClientPort);
            hbaseTestingUtility = new HBaseTestingUtility(conf);
            System.setProperty("zookeeper.4lw.commands.whitelist", "stat");
            zooKeeperCluster = hbaseTestingUtility.startMiniZKCluster(1, zooKeeperClientPort);
            hBaseCluster = hbaseTestingUtility.startMiniCluster();
            url = JDBC_PROTOCOL + JDBC_PROTOCOL_SEPARATOR + LOCALHOST + JDBC_PROTOCOL_SEPARATOR + zooKeeperClientPort
                    + JDBC_PROTOCOL_TERMINATOR + PHOENIX_TEST_DRIVER_URL_PARAM;
            driver = BaseTest.initAndRegisterTestDriver(url, ReadOnlyProps.EMPTY_PROPS);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public HBaseTestingUtility getHBaseTestUtility() {
        return hbaseTestingUtility;
    }

    public PhoenixTestDriver getDriver() {
        return driver;
    }

    public MiniZooKeeperCluster getZooKeeperCluster() {
        return zooKeeperCluster;
    }

    public MiniHBaseCluster getHBaseCluster() {
        return hBaseCluster;
    }

    public String getUrl() {
        return url;
    }
}
