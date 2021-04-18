import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Одна встроенная БД.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:xml/context.xml")
public class XmlInitDataSource {

    @Autowired
    private DataSource dataSource;

    @Test
    public void test() throws SQLException {
        AssertDataSource.assertDataSource(dataSource);
    }
}