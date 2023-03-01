package h2.connection.mode.embedded;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class CredentialsTest {
    /**
     * User you are connecting is created automatically.
     */
    @Test
    void autoCreatedUser() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:", "my_user", "my_pass")) {
            var st = conn.createStatement();
            var rs = st.executeQuery("SELECT * FROM INFORMATION_SCHEMA.USERS");
            if (rs.next()) {
                assertThat(rs.getString(1)).isEqualTo("MY_USER");
            }
        }
    }
}