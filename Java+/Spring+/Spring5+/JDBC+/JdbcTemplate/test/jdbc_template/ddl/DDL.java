package jdbc_template.ddl;

import org.junit.jupiter.api.Test;
import util.TestBase;

/**
 * Изменение структуры БД с помощью JdbcTemplate.
 */
class DDL extends TestBase {

    @Test
    void createTable() {
        template.execute("create table mytable (id integer, name varchar(100))");
    }
}