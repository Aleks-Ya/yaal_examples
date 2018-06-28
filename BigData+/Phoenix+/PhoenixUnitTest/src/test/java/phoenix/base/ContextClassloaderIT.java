package phoenix.base;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HBaseTestingUtility;
import org.apache.phoenix.query.BaseTest;
import org.apache.phoenix.query.QueryServices;
import org.apache.phoenix.util.ReadOnlyProps;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL;
import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL_SEPARATOR;
import static org.apache.phoenix.util.PhoenixRuntime.JDBC_PROTOCOL_TERMINATOR;
import static org.apache.phoenix.util.PhoenixRuntime.PHOENIX_TEST_DRIVER_URL_PARAM;
import static org.apache.phoenix.util.TestUtil.LOCALHOST;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ContextClassloaderIT extends BaseTest {

    private static HBaseTestingUtility hbaseTestUtil;
    private static ClassLoader badContextClassloader;

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

        Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE test (ID INTEGER NOT NULL PRIMARY KEY, NAME VARCHAR)");
        stmt.execute("UPSERT INTO test VALUES (1, 'name1')");
        stmt.execute("UPSERT INTO test VALUES (2, 'name2')");
        stmt.close();
        conn.commit();
        conn.close();
        badContextClassloader = new URLClassLoader(new URL[]{
                File.createTempFile("invalid", ".jar").toURI().toURL()}, null);
    }

    protected static String getUrl() {
        String url = "jdbc:phoenix:localhost:" + hbaseTestUtil.getZkCluster().getClientPort() + ";test=true";
        System.out.println("URL: " + url);
        return url;
    }

    @Test
    public void testQueryWithDifferentContextClassloader() throws InterruptedException {
        Runnable target = () -> {
            try {
                Connection conn = DriverManager.getConnection(getUrl());
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from test where name = 'name2'");
                while (rs.next()) {
                    // Just make sure we run over all records
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        ContextClassloaderIT.BadContextClassloaderThread t = new ContextClassloaderIT.BadContextClassloaderThread(target);
        t.start();
        t.join();
        assertFalse(t.failed);
    }

    @Test
    public void testGetDatabaseMetadataWithDifferentContextClassloader() throws InterruptedException {
        Runnable target = () -> {
            try {
                Connection conn = DriverManager.getConnection(getUrl());
                ResultSet tablesRs = conn.getMetaData().getTables(null, null, null, null);
                while (tablesRs.next()) {
                    // Just make sure we run over all records
                }
                tablesRs.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        ContextClassloaderIT.BadContextClassloaderThread t = new ContextClassloaderIT.BadContextClassloaderThread(target);
        t.start();
        t.join();
        assertFalse(t.failed);
    }

    @Test
    public void testExecuteDdlWithDifferentContextClassloader() throws InterruptedException {
        Runnable target = () -> {
            try {
                Connection conn = DriverManager.getConnection(getUrl());
                Statement stmt = conn.createStatement();
                stmt.execute("CREATE TABLE T2 (ID INTEGER NOT NULL PRIMARY KEY, NAME VARCHAR)");
                stmt.execute("UPSERT INTO T2 VALUES (1, 'name1')");
                conn.commit();
                ResultSet rs = stmt.executeQuery("SELECT * FROM T2");
                assertTrue(rs.next());
                assertFalse(rs.next());
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
        ContextClassloaderIT.BadContextClassloaderThread t = new ContextClassloaderIT.BadContextClassloaderThread(target);
        t.start();
        t.join();
        assertFalse(t.failed);
    }

    static class BadContextClassloaderThread extends Thread {

        private final Runnable target;
        boolean failed = false;

        public BadContextClassloaderThread(Runnable target) {
            super("BadContextClassloaderThread");
            this.target = target;
            setContextClassLoader(badContextClassloader);
        }

        @Override
        public void run() {
            try {
                target.run();
            } catch (Throwable t) {
                failed = true;
                throw new RuntimeException(t);
            }
        }

    }
}
