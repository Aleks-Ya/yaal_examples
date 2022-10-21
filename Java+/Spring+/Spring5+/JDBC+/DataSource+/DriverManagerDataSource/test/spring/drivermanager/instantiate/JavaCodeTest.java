package spring.drivermanager.instantiate;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Initialize DriverManagerDataSource programmatically.
 */
class JavaCodeTest {

    @Test
    void test() throws SQLException {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:");
        dataSource.setUsername("");
        dataSource.setPassword("");

        try (var conn = dataSource.getConnection();
             var st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE t1 (k INT PRIMARY KEY)");
            assertThat(st.executeUpdate("INSERT INTO t1 VALUES (3)")).isEqualTo(1);
        }
    }
}