package phoenix.factory;

import org.apache.phoenix.util.PropertiesUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import static org.apache.phoenix.util.TestUtil.TEST_PROPERTIES;
import static org.junit.Assert.assertEquals;


public class PhoenixTest {

    @Test
    public void test() throws Exception {
        PhoenixFactory phoenixFactory = new PhoenixFactory();
        String jdbcUrl = phoenixFactory.getUrl();

        Properties jdbcProps = PropertiesUtil.deepCopy(TEST_PROPERTIES);
        Connection conn = DriverManager.getConnection(jdbcUrl, jdbcProps);
        conn.setAutoCommit(false);

        String sampleDDL = "CREATE TABLE TEST_METRICS " +
                "(TEST_COLUMN VARCHAR " +
                "CONSTRAINT pk PRIMARY KEY (TEST_COLUMN)) " +
                "DATA_BLOCK_ENCODING='FAST_DIFF', IMMUTABLE_ROWS=true, " +
                "TTL=86400, COMPRESSION='NONE' ";

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sampleDDL);

        String insertQuery = "UPSERT INTO TEST_METRICS(TEST_COLUMN) VALUES ('1')";
        stmt.executeUpdate(insertQuery);

        conn.commit();

        ResultSet rs = stmt.executeQuery(
                "SELECT COUNT(TEST_COLUMN) FROM TEST_METRICS");

        rs.next();
        long l = rs.getLong(1);
        assertEquals(1, l);

        stmt.execute("DROP TABLE TEST_METRICS");
        conn.close();
    }
}
