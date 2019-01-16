package jdbc_template.ddl;

import jdbc_template.TestBase;
import org.junit.Test;

/**
 * Изменение структуры БД с помощью JdbcTemplate.
 */
public class DDL extends TestBase {

    @Test
    public void createTable() {
        template.execute("create table mytable (id integer, name varchar(100))");
    }
}