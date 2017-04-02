package jdbc_template;

import bean.Name;
import conf.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Выборка данных из БД с помощью JdbcTemplate.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class Select {
    private static final RowMapper<Name> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Name.class);

    @Autowired
    private JdbcTemplate template;

    @Test
    public void queryForObject() {
        assertEquals("Mary", template.queryForObject("SELECT title FROM names WHERE id=2", String.class));
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
        List<String> list = template.queryForList("SELECT title FROM names WHERE id=?",
                String.class, 1);
        assertThat(list, hasSize(1));
        assertEquals("John", list.get(0));
    }
}