import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Валидирует соответствие данного DataSource
 * скриптам инициализации (schems.sql и test-data.sql).
 */
class AssertDataSource {
    public static void assertDataSource(DataSource dataSource) throws SQLException {
        try (var conn = dataSource.getConnection();
             var st = conn.createStatement()) {
            var rs = st.executeQuery("SELECT * FROM names WHERE id=2");
            assertThat(rs.next()).isTrue();
            assertThat(rs.getString("title")).isEqualTo("H2");
        }
    }
}
