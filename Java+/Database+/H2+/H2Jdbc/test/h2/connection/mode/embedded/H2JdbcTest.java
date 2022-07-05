package h2.connection.mode.embedded;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class H2JdbcTest {

    @Test
    void test() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:")) {
            var st1 = conn.createStatement();
            st1.executeUpdate("DROP TABLE IF EXISTS numbers");
            st1.executeUpdate("CREATE TABLE numbers (numb INTEGER)");
            st1.executeUpdate("INSERT INTO numbers VALUES (3)");

            var st2 = conn.createStatement();
            var rs = st2.executeQuery("SELECT * FROM numbers");
            if (rs.next()) {
                assertThat(rs.getInt(1)).isEqualTo(3);
            }
        }
    }
}