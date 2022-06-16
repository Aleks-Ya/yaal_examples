package oracle;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class OracleTest {
    private static final String ORACLE_IMAGE = "gvenzl/oracle-xe";

    @Container
    private final OracleContainer oracle = new OracleContainer(ORACLE_IMAGE);

    @Test
    void test() throws SQLException {
        assertThat(oracle.isRunning()).isTrue();
        var url = oracle.getJdbcUrl();
        var username = oracle.getUsername();
        var password = oracle.getPassword();
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
