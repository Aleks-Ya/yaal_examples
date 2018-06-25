package phoenix;

import org.apache.hadoop.hbase.IntegrationTestingUtility;
import org.apache.phoenix.hbase.index.write.IndexWriterUtils;
import org.apache.phoenix.query.BaseTest;
import org.apache.phoenix.query.QueryServices;
import org.apache.phoenix.util.PropertiesUtil;
import org.apache.phoenix.util.ReadOnlyProps;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.phoenix.util.TestUtil.TEST_PROPERTIES;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.apache.curator.shaded.com.google.common.io.Files;


public class PhoenixTest  extends BaseTest {

    protected static final long BATCH_SIZE = 3;
    @Test
    public void test() throws Exception {
        Map<String, String> props = new HashMap<>();
        // Must update config before starting server
        props.put(QueryServices.STATS_USE_CURRENT_TIME_ATTRIB, Boolean.FALSE.toString());
        props.put("java.security.krb5.realm", "");
        props.put("java.security.krb5.kdc", "");
        props.put(IntegrationTestingUtility.IS_DISTRIBUTED_CLUSTER, "false");
        props.put(QueryServices.QUEUE_SIZE_ATTRIB, Integer.toString(5000));
        props.put(IndexWriterUtils.HTABLE_THREAD_KEY, Integer.toString(100));
        // Make a small batch size to test multiple calls to reserve sequences
        props.put(QueryServices.SEQUENCE_CACHE_SIZE_ATTRIB, Long.toString(BATCH_SIZE));
        // Must update config before starting server
        setUpTestDriver(new ReadOnlyProps(props.entrySet().iterator()));


        Properties jdbcProps = PropertiesUtil.deepCopy(TEST_PROPERTIES);
        Connection conn = DriverManager.getConnection(getUrl(), jdbcProps);



        conn.setAutoCommit(true);

        String sampleDDL = "CREATE TABLE TEST_METRICS " +
                "(TEST_COLUMN VARCHAR " +
                "CONSTRAINT pk PRIMARY KEY (TEST_COLUMN)) " +
                "DATA_BLOCK_ENCODING='FAST_DIFF', IMMUTABLE_ROWS=true, " +
                "TTL=86400, COMPRESSION='NONE' ";

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sampleDDL);
        conn.commit();

        ResultSet rs = stmt.executeQuery(
                "SELECT COUNT(TEST_COLUMN) FROM TEST_METRICS");

        rs.next();
        long l = rs.getLong(1);
        assertTrue(l >= 0);

        stmt.execute("DROP TABLE TEST_METRICS");
        conn.close();
    }
}
