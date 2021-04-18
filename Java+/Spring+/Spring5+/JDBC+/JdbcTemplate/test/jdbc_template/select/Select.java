package jdbc_template.select;

import org.junit.jupiter.api.Test;
import util.TestBase;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Выборка данных из БД с помощью JdbcTemplate.
 */
public class Select extends TestBase {

    @Test
    public void queryForObject() {
        assertEquals("Mary", template.queryForObject("SELECT title FROM names WHERE id=2", String.class));
    }

    @Test
    public void queryPrimitiveList() {
        List<Integer> list = template.queryForList("SELECT id FROM names", Integer.class);
        assertThat(list, containsInAnyOrder(1, 2, 4));
    }

    @Test
    public void queryForObjectWithParameter() {
        assertEquals(2, (int) template.queryForObject(
                "SELECT id FROM names WHERE title = ?", Integer.class, "Mary"));
    }

    @Test
    public void rowsCount() {
        assertEquals(3, (int) template.queryForObject("SELECT count(*) FROM names", Integer.class));
    }

    @Test
    public void queryForList() {
        List<String> list = template.queryForList("SELECT title FROM names WHERE id=?", String.class, 1);
        assertThat(list, hasSize(1));
        assertEquals("John", list.get(0));
    }

    @Test
    public void whereIn() {
        List<String> list = template.queryForList("SELECT title FROM names WHERE id IN(1,4)", String.class);
        assertThat(list, containsInAnyOrder("John", "Ben"));
    }
}