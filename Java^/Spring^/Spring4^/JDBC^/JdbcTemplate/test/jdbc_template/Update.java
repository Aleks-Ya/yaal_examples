package jdbc_template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Внесение изменений в БД с помощью JdbcTemplate.
 */
@ContextConfiguration("classpath:context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class Update {

    @Autowired
    private JdbcTemplate template;

    @Test
    public void insert() {
        assertEquals(1, template.update("INSERT INTO names values(3, 'Vera')"));
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