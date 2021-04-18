package jdbc_template.ddl;

import org.junit.jupiter.api.Test;
import util.TestBase;

/**
 * Изменение структуры БД с помощью JdbcTemplate.
 */
public class DDL extends TestBase {

    @Test
    public void createTable() {
        template.execute("create table mytable (id integer, name varchar(100))");
    }
}