package postgres;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testcontainers.containers.PostgreSQLContainer.IMAGE;

public class PostgreSqlTest {
    private static final String postgresImage = IMAGE + ":12";

    @Rule
    public PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(postgresImage);

    @Test
    public void test() throws SQLException {
        var url = postgres.getJdbcUrl();
        var username = postgres.getUsername();
        var password = postgres.getPassword();
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
