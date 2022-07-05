import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Одна встроенная БД.
 */
@ContextConfiguration("classpath:xml/context-one-db.xml")
@RunWith(SpringJUnit4ClassRunner.class)
class XmlOneDbTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void test() throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM names WHERE id=2");
            assertThat(rs.next()).isTrue();
            assertThat(rs.getString("title")).isEqualTo("H2");
        }
    }
}