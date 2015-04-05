import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

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
@ContextConfiguration("classpath:xml/context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
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
    public void tomcatPool() throws SQLException {
        testDataSource(tomcatPoolDataSource);
    }

    @Test
    public void boneCp() throws SQLException {
        testDataSource(boneCpDataSource);
    }

    @Test
    public void apacheCommonsDbcp() throws SQLException {
        testDataSource(apacheCommonsDataSource);
    }

    @Test
    public void hikari() throws SQLException {
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