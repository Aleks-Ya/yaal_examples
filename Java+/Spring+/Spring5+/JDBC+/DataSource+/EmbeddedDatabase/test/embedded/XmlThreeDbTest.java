package embedded;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Все три встроенные БД: h2, HSQL, Derby.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:xml/context-three-db.xml")
class XmlThreeDbTest {

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
    void h2() throws SQLException {
        testDbContent(h2DataSource, "H2");
    }

    @Test
    void hsql() throws SQLException {
        testDbContent(hsqlDataSource, "Hsql");
    }

    @Test
    void derby() throws SQLException {
        testDbContent(derbyDataSource, "Derby");
    }

    private void testDbContent(DataSource dataSource, String text) throws SQLException {
        try (var conn = dataSource.getConnection();
             var st = conn.createStatement()) {
            var rs = st.executeQuery("SELECT * FROM names WHERE id=2");
            assertThat(rs.next()).isTrue();
            assertThat(rs.getString("title")).isEqualTo(text);
        }
    }
}