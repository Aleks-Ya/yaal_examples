package xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Все три встроенные БД: h2, HSQL, Derby.
 */
@ContextConfiguration("classpath:xml/context-three-db.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ThreeDbXml {

    @Autowired
    @Qualifier("h2dataSource")
    private DataSource h2DataSource;

    @Autowired
    @Qualifier("hsqlDataSource")
    private DataSource hsqlDataSource;

    @Autowired
    @Qualifier("derbyDataSource")
    private DataSource derbyDataSource;

    @Test
    public void h2() throws SQLException {
        testDbContent(h2DataSource, "H2");
    }

    @Test
    public void hsql() throws SQLException {
        testDbContent(hsqlDataSource, "Hsql");
    }

    @Test
    public void derby() throws SQLException {
        testDbContent(derbyDataSource, "Derby");
    }

    private void testDbContent(DataSource dataSource, String text) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM names WHERE id=2");
            assertTrue(rs.next());
            assertEquals(text, rs.getString("title"));
        }
    }
}