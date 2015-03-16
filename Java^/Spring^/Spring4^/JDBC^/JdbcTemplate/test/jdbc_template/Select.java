package jdbc_template;

import bean.Name;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Выборка данных из БД с помощью JdbcTemplate.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Select {

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
        Name name = template.queryForObject("SELECT * FROM names WHERE id=1", new NameRowMapper());
        assertEquals(1, (int) name.getId());
        assertEquals("John", name.getTitle());
    }

    @Test
    public void rowMapperForObjectList() {
        List<Name> names = template.query("SELECT * FROM names", new NameRowMapper());

        Name name1 = names.get(0);
        assertEquals(1, (int) name1.getId());
        assertEquals("John", name1.getTitle());

        Name name2 = names.get(1);
        assertEquals(2, (int) name2.getId());
        assertEquals("Mary", name2.getTitle());
    }

    static class NameRowMapper implements RowMapper<Name> {
        @Override
        public Name mapRow(ResultSet rs, int rowNum) throws SQLException {
            Integer id = rs.getInt("id");
            String title = rs.getString("title");
            return new Name(id, title);
        }
    }
}