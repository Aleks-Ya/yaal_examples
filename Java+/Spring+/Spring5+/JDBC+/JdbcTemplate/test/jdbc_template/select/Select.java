package jdbc_template.select;

import bean.Name;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import util.TestBase;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Выборка данных из БД с помощью JdbcTemplate.
 */
public class Select extends TestBase {
    private static final RowMapper<Name> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Name.class);

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
    public void rowMapperForSingleObject() {
        Name name = template.queryForObject("SELECT * FROM names WHERE id=1", ROW_MAPPER);
        assertEquals(1, (int) name.getId());
        assertEquals("John", name.getTitle());
    }

    @Test
    public void rowMapperForObjectList() {
        List<Name> names = template.query("SELECT * FROM names", ROW_MAPPER);

        Name name1 = names.get(0);
        assertEquals(1, (int) name1.getId());
        assertEquals("John", name1.getTitle());

        Name name2 = names.get(1);
        assertEquals(2, (int) name2.getId());
        assertEquals("Mary", name2.getTitle());
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