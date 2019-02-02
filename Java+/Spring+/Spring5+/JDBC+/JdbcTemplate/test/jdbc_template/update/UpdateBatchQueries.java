package jdbc_template.update;

import org.junit.Test;
import util.AssertPrimitiveArrays;
import util.TestBase;

import java.util.Arrays;
import java.util.List;

import static util.AssertPrimitiveArrays.assertArray;

/**
 * Use batch update with a batch of queries.
 */
public class UpdateBatchQueries extends TestBase {

    @Test
    public void insert() {
        String[] queries = {
                "INSERT INTO names values(5, 'Vera')",
                "INSERT INTO names values(6, 'Bagira')"};
        int[] result = template.batchUpdate(queries);
        assertArray(result, new int[]{1, 1});
    }

    @Test
    public void update() {
        String[] queries = {
                "UPDATE names SET title='Jerry' WHERE title='John'",
                "UPDATE names SET title='Boris' WHERE id=600"};
        int[] result = template.batchUpdate(queries);
        assertArray(result, new int[]{1, 0});
    }

    @Test
    public void delete() {
        String[] queries = {
                "DELETE FROM names WHERE title='Mary'",
                "DELETE FROM names WHERE title='John'"};
        int[] result = template.batchUpdate(queries);
        assertArray(result, new int[]{1, 1});
    }

}