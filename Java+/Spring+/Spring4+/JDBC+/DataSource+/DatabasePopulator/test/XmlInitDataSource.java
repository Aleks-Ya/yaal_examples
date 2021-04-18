import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Одна встроенная БД.
 */
@ContextConfiguration("classpath:xml/context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class XmlInitDataSource {

    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        AssertDataSource.assertDataSource(dataSource);
    }
}