package jdbc_template.update;

import org.junit.jupiter.api.Test;
import util.TestBase;

import static util.AssertPrimitiveArrays.assertArray;

/**
 * Use batch update with a batch of queries.
 */
class UpdateBatchQueries extends TestBase {

    @Test
    void insert() {
        var queries = new String[]{
                "INSERT INTO names values(5, 'Vera')",
                "INSERT INTO names values(6, 'Bagira')"};
        var result = template.batchUpdate(queries);
        assertArray(result, new int[]{1, 1});
    }

    @Test
    void update() {
        var queries = new String[]{
                "UPDATE names SET title='Jerry' WHERE title='John'",
                "UPDATE names SET title='Boris' WHERE id=600"};
        var result = template.batchUpdate(queries);
        assertArray(result, new int[]{1, 0});
    }

    @Test
    void delete() {
        var queries = new String[]{
                "DELETE FROM names WHERE title='Mary'",
                "DELETE FROM names WHERE title='John'"};
        var result = template.batchUpdate(queries);
        assertArray(result, new int[]{1, 1});
    }

}