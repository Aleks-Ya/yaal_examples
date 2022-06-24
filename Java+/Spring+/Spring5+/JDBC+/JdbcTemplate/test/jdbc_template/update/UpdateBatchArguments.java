package jdbc_template.update;

import org.junit.jupiter.api.Test;
import util.TestBase;

import java.util.Arrays;
import java.util.List;

import static util.AssertPrimitiveArrays.assertArray;

/**
 * Use batch update with a batch of arguments.
 */
public class UpdateBatchArguments extends TestBase {

    @Test
    void insert() {
        List<Object[]> values = Arrays.asList(
                new Object[]{7, "Vera"},
                new Object[]{8, "Bagira"});
        int[] result = template.batchUpdate("INSERT INTO names values(?, ?)", values);
        assertArray(result, new int[]{1, 1});
    }

    @Test
    void update() {
        List<Object[]> values = Arrays.asList(
                new Object[]{"Yram", "Mary"},
                new Object[]{"Nohj", "John"});
        int[] result = template.batchUpdate("UPDATE names SET title=? WHERE title=?", values);
        assertArray(result, new int[]{1, 1});
    }

    @Test
    void delete() {
        List<Object[]> values = Arrays.asList(
                new Object[]{"Mary"},
                new Object[]{"John"});
        int[] result = template.batchUpdate("DELETE FROM names WHERE title=?", values);
        assertArray(result, new int[]{1, 1});
    }
}