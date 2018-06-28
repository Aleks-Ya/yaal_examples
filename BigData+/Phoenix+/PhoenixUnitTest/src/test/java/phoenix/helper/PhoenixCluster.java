package phoenix.helper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.phoenix.query.BaseTest;
import org.apache.phoenix.query.QueryServices;
import org.apache.phoenix.util.ReadOnlyProps;

import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL;
import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL_SEPARATOR;
import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL_TERMINATOR;
import static org.apache.phoenix.util.PhoenixRuntime.PHOENIX_TEST_DRIVER_URL_PARAM;
import static org.apache.phoenix.util.TestUtil.LOCALHOST;

public class PhoenixCluster {

    private HBaseTestingUtility hbaseTestUtil;

    public void init() throws Exception {
        Configuration conf = HBaseConfiguration.create();
        BaseTest.setUpConfigForMiniCluster(conf);
        System.setProperty("zookeeper.4lw.commands.whitelist", "stat");
        hbaseTestUtil = new HBaseTestingUtility(conf);
        hbaseTestUtil.startMiniZKCluster(1, 2181);
        hbaseTestUtil.startMiniCluster();
        String clientPort = hbaseTestUtil.getConfiguration().get(QueryServices.ZOOKEEPER_PORT_ATTRIB);
        String url = JDBC_PROTOCOL + JDBC_PROTOCOL_SEPARATOR + LOCALHOST + JDBC_PROTOCOL_SEPARATOR + clientPort
                + JDBC_PROTOCOL_TERMINATOR + PHOENIX_TEST_DRIVER_URL_PARAM;
        BaseTest.initAndRegisterTestDriver(url, ReadOnlyProps.EMPTY_PROPS);
    }

    public String getUrl() {
        return "jdbc:phoenix:localhost:" + hbaseTestUtil.getZkCluster().getClientPort() + ";test=true";
    }

}
