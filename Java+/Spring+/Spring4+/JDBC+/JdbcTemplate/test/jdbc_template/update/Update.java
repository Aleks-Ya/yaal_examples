package jdbc_template.update;

import conf.Config;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Внесение изменений в БД с помощью JdbcTemplate.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class Update {

    @Autowired
    private JdbcTemplate template;

    @Test
    void insert() {
        String name = "Vera";
        assertEquals(1, template.update("INSERT INTO names values(3, ?)", name));
    }

    @Test
    void update() {
        assertEquals(1, template.update("UPDATE names SET title='Jerry' WHERE title='John'"));
    }

    @Test
    void delete() {
        assertEquals(1, template.update("DELETE FROM names WHERE title='Mary'"));
    }
}