package instantiate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Инициализация DriverManagerDataSource в XML.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:context.xml")
public class Xml {

    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE t1 (k INT PRIMARY KEY)");
            assertEquals(1, st.executeUpdate("INSERT INTO t1 VALUES (3)"));
        }
    }
}