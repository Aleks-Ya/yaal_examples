package postgres;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.containers.PostgreSQLContainer.IMAGE;

@Testcontainers
class ConnectTest {
    @Container
    private final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(IMAGE + ":14");

    @Test
    void connect() throws SQLException {
        var url = postgres.getJdbcUrl();
        var username = postgres.getUsername();
        var password = postgres.getPassword();
        try (var conn = DriverManager.getConnection(url, username, password)) {
            var update = conn.createStatement();
            update.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
            update.executeUpdate("INSERT INTO numbers VALUES (3)");

            var select = conn.createStatement();
            var resultSet = select.executeQuery("SELECT * FROM numbers");
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt(1)).isEqualTo(3);
            assertThat(resultSet.next()).isFalse();
        }
    }
}