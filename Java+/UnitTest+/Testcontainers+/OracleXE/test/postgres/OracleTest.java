package postgres;

import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.OracleContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OracleTest {

    @Rule
    public OracleContainer oracle = new OracleContainer("wnameless/oracle-xe-11g-r2");

    @Test
    public void test() throws SQLException {
        String url = oracle.getJdbcUrl();
        String username = oracle.getUsername();
        String password = oracle.getPassword();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            Statement update = conn.createStatement();
            update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
            update.executeUpdate("INSERT INTO numbers VALUES (3)");

            Statement select = conn.createStatement();
            ResultSet resultSet = select.executeQuery("SELECT * FROM numbers");
            assertTrue(resultSet.next());
            assertThat(resultSet.getInt(1), equalTo(3));
            assertFalse(resultSet.next());
        }
    }
}
