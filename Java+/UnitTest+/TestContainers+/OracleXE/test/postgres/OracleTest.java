package postgres;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.OracleContainer;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OracleTest {
    private static final String ORACLE_IMAGE = "wnameless/oracle-xe-11g-r2";

    @Rule
    public OracleContainer oracle = new OracleContainer(ORACLE_IMAGE);

    @Test
    public void test() throws SQLException {
        var url = oracle.getJdbcUrl();
        var username = oracle.getUsername();
        var password = oracle.getPassword();
        try (var conn = DriverManager.getConnection(url, username, password)) {
            var update = conn.createStatement();
            update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
            update.executeUpdate("INSERT INTO numbers VALUES (3)");

            var select = conn.createStatement();
            var resultSet = select.executeQuery("SELECT * FROM numbers");
            assertTrue(resultSet.next());
            assertThat(resultSet.getInt(1), equalTo(3));
            assertFalse(resultSet.next());
        }
    }
}
