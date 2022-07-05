package jdbc_template.select;

import bean.Name;
import conf.Config;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Выборка данных из БД с помощью JdbcTemplate.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
class SelectTest {
    private static final RowMapper<Name> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Name.class);

    @Autowired
    private JdbcTemplate template;

    @Test
    void queryForObject() {
        assertThat(template.queryForObject("SELECT title FROM names WHERE id=2", String.class)).isEqualTo("Mary");
    }

    @Test
    void queryPrimitiveList() {
        var list = template.queryForList("SELECT id FROM names", Integer.class);
        assertThat(list).containsExactlyInAnyOrder(1, 2, 4);
    }

    @Test
    void queryForObjectWithParameter() {
        assertThat((int) template.queryForObject(
                "SELECT id FROM names WHERE title = ?", Integer.class, "Mary")).isEqualTo(2);
    }

    @Test
    void rowsCount() {
        assertThat((int) template.queryForObject("SELECT count(*) FROM names", Integer.class)).isEqualTo(3);
    }

    @Test
    void rowMapperForSingleObject() {
        var name = template.queryForObject("SELECT * FROM names WHERE id=1", ROW_MAPPER);
        assertThat((int) name.getId()).isEqualTo(1);
        assertThat(name.getTitle()).isEqualTo("John");
    }

    @Test
    void rowMapperForObjectList() {
        var names = template.query("SELECT * FROM names", ROW_MAPPER);

        var name1 = names.get(0);
        assertThat((int) name1.getId()).isEqualTo(1);
        assertThat(name1.getTitle()).isEqualTo("John");

        var name2 = names.get(1);
        assertThat((int) name2.getId()).isEqualTo(2);
        assertThat(name2.getTitle()).isEqualTo("Mary");
    }

    @Test
    void queryForList() {
        var list = template.queryForList("SELECT title FROM names WHERE id=?", String.class, 1);
        assertThat(list).hasSize(1);
        assertThat(list.get(0)).isEqualTo("John");
    }

    @Test
    void whereIn() {
        var list = template.queryForList("SELECT title FROM names WHERE id IN(1,4)", String.class);
        assertThat(list).containsExactlyInAnyOrder("John", "Ben");
    }
}