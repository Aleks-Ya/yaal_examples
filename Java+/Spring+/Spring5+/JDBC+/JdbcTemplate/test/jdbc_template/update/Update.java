package jdbc_template.update;

import org.junit.jupiter.api.Test;
import util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Внесение изменений в БД с помощью JdbcTemplate.
 */
class Update extends TestBase {

    @Test
    void insert() {
        String name = "Vera";
        assertThat(template.update("INSERT INTO names values(3, ?)", name)).isEqualTo(1);
    }

    @Test
    void update() {
        assertThat(template.update("UPDATE names SET title='Jerry' WHERE title='John'")).isEqualTo(1);
    }

    @Test
    void delete() {
        assertThat(template.update("DELETE FROM names WHERE title='Mary'")).isEqualTo(1);
    }
}