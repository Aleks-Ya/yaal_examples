package jdbc_template.update;

import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Внесение изменений в БД с помощью JdbcTemplate.
 */
public class Update extends TestBase {

    @Test
    public void insert() {
        String name = "Vera";
        assertEquals(1, template.update("INSERT INTO names values(3, ?)", name));
    }

    @Test
    public void update() {
        assertEquals(1, template.update("UPDATE names SET title='Jerry' WHERE title='John'"));
    }

    @Test
    public void delete() {
        assertEquals(1, template.update("DELETE FROM names WHERE title='Mary'"));
    }
}