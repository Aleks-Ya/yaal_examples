package jdbc_template.ddl;

import conf.Config;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Изменение структуры БД с помощью JdbcTemplate.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.class)
public class DDL {

    @Autowired
    private JdbcTemplate template;

    @Test
    public void createTable() {
        template.execute("create table mytable (id integer, name varchar(100))");
    }
}