package hive.jdbc.metastore;

import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Load metastore's JDBC connection params via Hive JDBC connection.
 */
public class HiveJdbcMetastoreConnectionTest {

    @Test
    public void loadConnectionParams() throws SQLException {
        HiveJdbcMetastoreConnection.ConnectionParams cp = HiveJdbcMetastoreConnection.loadMetastoreConnectionParams("jdbc:hive2://localhost:10000");
        assertThat(cp.getUrl(), equalTo("jdbc:derby:;databaseName=metastore_db;create=true"));
        assertThat(cp.getDriver(), equalTo("org.apache.derby.jdbc.EmbeddedDriver"));
        assertThat(cp.getUser(), equalTo("APP"));
        assertThat(cp.getPassword(), equalTo("mine"));
    }
}
