package hive.jdbc;

import org.junit.Test;

import java.sql.*;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Connect to Hive via JDBC.
 */
public class HiveJdbcConnectionTest {

    @Test
    public void connect() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:hive2://localhost:10000");
            Statement statement = connection.createStatement();
            int res = statement.executeUpdate("CREATE TABLE IF NOT EXISTS people (name string, age INT)");
            assertThat(res, equalTo(0));
            ResultSet resultSet = statement.executeQuery("SHOW TABLES");
            boolean next = resultSet.next();
            assertTrue(next);
            String table = resultSet.getString(1);
            assertThat(table, equalTo("people"));

            next = resultSet.next();
            assertFalse(next);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
