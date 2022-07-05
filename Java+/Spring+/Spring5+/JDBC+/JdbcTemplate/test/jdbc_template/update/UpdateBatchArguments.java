package jdbc_template.update;

import org.junit.jupiter.api.Test;
import util.TestBase;

import java.util.Arrays;

import static util.AssertPrimitiveArrays.assertArray;

/**
 * Use batch update with a batch of arguments.
 */
class UpdateBatchArguments extends TestBase {

    @Test
    void insert() {
        var values = Arrays.asList(
                new Object[]{7, "Vera"},
                new Object[]{8, "Bagira"});
        var result = template.batchUpdate("INSERT INTO names values(?, ?)", values);
        assertArray(result, new int[]{1, 1});
    }

    @Test
    void update() {
        var values = Arrays.asList(
                new Object[]{"Yram", "Mary"},
                new Object[]{"Nohj", "John"});
        var result = template.batchUpdate("UPDATE names SET title=? WHERE title=?", values);
        assertArray(result, new int[]{1, 1});
    }

    @Test
    void delete() {
        var values = Arrays.asList(
                new Object[]{"Mary"},
                new Object[]{"John"});
        var result = template.batchUpdate("DELETE FROM names WHERE title=?", values);
        assertArray(result, new int[]{1, 1});
    }
}