package jdbc;

import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Instantiate object {@link java.sql.Array}.
 */
class InstantiateSqlArray {

    @Test
    void test() throws SQLException {
        try (var conn = DriverManager.getConnection("jdbc:h2:mem:")) {
            Object[] expJavaArray = {"a", "b"};

            var sqlArray = conn.createArrayOf(String.class.getName(), expJavaArray);
            assertThat(sqlArray).isInstanceOf(java.sql.Array.class);

            var actJavaArray = (Object[]) sqlArray.getArray();
            assertThat(actJavaArray).isEqualTo(expJavaArray);
        }
    }
}