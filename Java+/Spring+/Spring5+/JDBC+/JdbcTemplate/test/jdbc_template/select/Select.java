package jdbc_template.select;

import org.junit.jupiter.api.Test;
import util.TestBase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Выборка данных из БД с помощью JdbcTemplate.
 */
class Select extends TestBase {

    @Test
    void queryForObject() {
        assertThat(template.queryForObject("SELECT title FROM names WHERE id=2", String.class)).isEqualTo("Mary");
    }

    @Test
    void queryPrimitiveList() {
        List<Integer> list = template.queryForList("SELECT id FROM names", Integer.class);
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
    void queryForList() {
        List<String> list = template.queryForList("SELECT title FROM names WHERE id=?", String.class, 1);
        assertThat(list).hasSize(1);
        assertThat(list.get(0)).isEqualTo("John");
    }

    @Test
    void whereIn() {
        List<String> list = template.queryForList("SELECT title FROM names WHERE id IN(1,4)", String.class);
        assertThat(list).containsExactlyInAnyOrder("John", "Ben");
    }
}