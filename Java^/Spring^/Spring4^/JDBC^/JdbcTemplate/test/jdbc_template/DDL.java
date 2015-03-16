package jdbc_template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Изменение структуры БД с помощью JdbcTemplate.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DDL {

    @Autowired
    private JdbcTemplate template;

    @Test
    public void createTable() {
        template.execute("create table mytable (id integer, name varchar(100))");
    }
}