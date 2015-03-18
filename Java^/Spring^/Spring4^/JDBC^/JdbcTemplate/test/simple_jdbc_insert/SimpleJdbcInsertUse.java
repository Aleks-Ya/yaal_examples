package simple_jdbc_insert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

/**
 * Вставка строк в БД с помощью SimpleJdbcInsert.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleJdbcInsertUse {

    @Autowired
    private JdbcTemplate template;

    private SimpleJdbcInsert insert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        insert = new SimpleJdbcInsert(dataSource)
                .withTableName("names")
                .usingGeneratedKeyColumns("id");
    }

    @Test
    public void insert() {

        assertEquals(1, template.update("INSERT INTO names values(3, 'Vera')"));
    }
}