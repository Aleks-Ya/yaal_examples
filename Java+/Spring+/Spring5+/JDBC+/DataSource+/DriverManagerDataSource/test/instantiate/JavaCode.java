package instantiate;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

/**
 * Инициализация DriverManagerDataSource в Java-коде.
 */
public class JavaCode {

    @Test
    public void test() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:");
        dataSource.setUsername("");
        dataSource.setPassword("");

        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE t1 (k INT PRIMARY KEY)");
            assertEquals(1, st.executeUpdate("INSERT INTO t1 VALUES (3)"));
        }
    }
}