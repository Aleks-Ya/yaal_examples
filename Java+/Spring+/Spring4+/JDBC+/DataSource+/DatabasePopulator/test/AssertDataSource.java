import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Валидирует соответствие данного DataSource
 * скриптам инициализации (schems.sql и test-data.sql).
 */
class AssertDataSource {
    public static void assertDataSource(DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM names WHERE id=2");
            assertTrue(rs.next());
            assertEquals("H2", rs.getString("title"));
        }
    }
}
