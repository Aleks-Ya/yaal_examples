import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Создание DataSource на основе пула соединений
 * с помощью XML-конфигурации.
 * <p/>
 * Использованные реализации JDBC-пула:
 * 1. Tomcat JDBC Connection Pool
 * 2. BoneCP
 * 3. Apache Commons DBCP
 * 4. HikariCP
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:xml/context.xml")
public class JdbcPoolXml {

    @Autowired
    @Qualifier("tomcatPoolDataSource")
    private DataSource tomcatPoolDataSource;

    @Autowired
    @Qualifier("boneCpDataSource")
    private DataSource boneCpDataSource;

    @Autowired
    @Qualifier("apacheCommonsDataSource")
    private DataSource apacheCommonsDataSource;

    @Autowired
    @Qualifier("hikariDataSource")
    private DataSource hikariDataSource;

    @Test
    void tomcatPool() throws SQLException {
        testDataSource(tomcatPoolDataSource);
    }

    @Test
    void boneCp() throws SQLException {
        testDataSource(boneCpDataSource);
    }

    @Test
    void apacheCommonsDbcp() throws SQLException {
        testDataSource(apacheCommonsDataSource);
    }

    @Test
    void hikari() throws SQLException {
        testDataSource(hikariDataSource);
    }

    private void testDataSource(DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()
        ) {
            st.executeUpdate("CREATE TABLE t (a int)");
            assertEquals(1, st.executeUpdate("INSERT INTO t VALUES (1)"));
        }
    }
}