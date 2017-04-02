package jdbc_template.ddl;

import conf.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Изменение структуры БД с помощью JdbcTemplate.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class DDL {

    @Autowired
    private JdbcTemplate template;

    @Test
    public void createTable() {
        template.execute("create table mytable (id integer, name varchar(100))");
    }
}