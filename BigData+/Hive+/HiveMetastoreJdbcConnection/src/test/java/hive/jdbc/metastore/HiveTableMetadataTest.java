package hive.jdbc.metastore;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Load metastore's JDBC connection params via Hive JDBC connection.
 */
public class HiveTableMetadataTest {

    @Test
    public void loadConnectionParams() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:hive2://localhost:10000");
            String tableName = "men";
            HiveTableMetadata.TableMetaInfo cp = HiveTableMetadata.loadTableMetadata(conn, tableName);
            assertThat(cp.getTableName(), equalTo(tableName));
            assertThat(cp.getCreateTime(), notNullValue());
            assertThat(cp.getLastModifiedTime(), notNullValue());
            assertThat(cp.getTransientLastDdlTime(), notNullValue());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
