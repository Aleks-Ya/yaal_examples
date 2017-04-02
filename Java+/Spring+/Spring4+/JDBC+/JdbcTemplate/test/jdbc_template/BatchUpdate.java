package jdbc_template;

import conf.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Внесение ПАКЕТНЫХ изменений в БД с помощью JdbcTemplate.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class BatchUpdate {

    @Autowired
    private JdbcTemplate template;

    @Test
    public void insert() {
        assertArray(template.batchUpdate(
                "INSERT INTO names values(1, 'Vera')",
                "INSERT INTO names values(2, 'Bagira')"
        ), new int[]{1,1});
    }

    @Test
    public void update() {
        assertArray(template.batchUpdate(
                "UPDATE names SET title='Jerry' WHERE title='Vera'",
                "UPDATE names SET title='Boris' WHERE id=600"
        ), new int[] {1,0});
    }

    @Test
    public void delete() {
        assertArray(template.batchUpdate(
                "DELETE FROM names WHERE title='Mary'",
                "DELETE FROM names WHERE title='John'"
        ), new int[]{1,1});
    }

    /**
     * Hamcrest невозможно использовать с массивами примитивов
     * (см. example ArrayAssert).
     */
    private void assertArray(int[] actual, int[] expected) {
        if (actual == null) {
            throw new AssertionError("Actual array is null");
        }
        if (expected == null) {
            throw new AssertionError("Expected array is null");
        }
        if (actual.length != expected.length) {
            throw new AssertionError("Length mismatch: expected " + expected.length
                    + " actual " + actual.length);
        }
        for (int i = 0; i < actual.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Value mismatch: expected " + expected[i]
                        + " actual " + actual[i]);
            }
        }
    }
}