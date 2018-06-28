package phoenix.base;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.phoenix.query.BaseTest;
import org.apache.phoenix.query.QueryServices;
import org.apache.phoenix.util.ReadOnlyProps;
import org.junit.BeforeClass;

import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL;
import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL_SEPARATOR;
import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL_TERMINATOR;
import static org.apache.phoenix.util.PhoenixRuntime.PHOENIX_TEST_DRIVER_URL_PARAM;
import static org.apache.phoenix.util.TestUtil.LOCALHOST;

public abstract class PhoenixBaseTest extends BaseTest {

    private static HBaseTestingUtility hbaseTestUtil;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        Configuration conf = HBaseConfiguration.create();
        setUpConfigForMiniCluster(conf);
        System.setProperty("zookeeper.4lw.commands.whitelist", "stat");
        hbaseTestUtil = new HBaseTestingUtility(conf);
        hbaseTestUtil.startMiniZKCluster(1, 2181);
        hbaseTestUtil.startMiniCluster();
        String clientPort = hbaseTestUtil.getConfiguration().get(QueryServices.ZOOKEEPER_PORT_ATTRIB);
        String url = JDBC_PROTOCOL + JDBC_PROTOCOL_SEPARATOR + LOCALHOST + JDBC_PROTOCOL_SEPARATOR + clientPort
                + JDBC_PROTOCOL_TERMINATOR + PHOENIX_TEST_DRIVER_URL_PARAM;
        driver = initAndRegisterTestDriver(url, ReadOnlyProps.EMPTY_PROPS);
    }

    protected static String getJdbcUrl() {
        return "jdbc:phoenix:localhost:" + hbaseTestUtil.getZkCluster().getClientPort() + ";test=true";
    }

}
