package jdbc_template.update;

import conf.Config;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Внесение изменений в БД с помощью JdbcTemplate.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
class Update {

    @Autowired
    private JdbcTemplate template;

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