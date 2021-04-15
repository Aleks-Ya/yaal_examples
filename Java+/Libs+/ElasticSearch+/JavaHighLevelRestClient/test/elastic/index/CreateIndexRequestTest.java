package elastic.index;

import org.junit.Test;

import static elastic.ConnectionHelper.createIndex;
import static elastic.ConnectionHelper.createRandomIndexName;
import static elastic.ConnectionHelper.deleteIndex;
import static elastic.ConnectionHelper.isIndexExist;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateIndexRequestTest {
    @Test
    public void createAndDeleteIndex() {
        String indexName = createRandomIndexName();
        assertFalse(isIndexExist(indexName));
        assertTrue(createIndex(indexName).isAcknowledged());
        assertTrue(isIndexExist(indexName));
        assertTrue(deleteIndex(indexName).isAcknowledged());
        assertFalse(isIndexExist(indexName));
    }
}
