package elastic.index;

import org.junit.Test;

import static elastic.EsHelper.createIndex;
import static elastic.EsHelper.createRandomIndexName;
import static elastic.EsHelper.deleteIndex;
import static elastic.EsHelper.isIndexExist;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateIndexRequestTest {
    @Test
    public void createAndDeleteIndex() {
        var indexName = createRandomIndexName();
        assertFalse(isIndexExist(indexName));
        assertTrue(createIndex(indexName).isAcknowledged());
        assertTrue(isIndexExist(indexName));
        assertTrue(deleteIndex(indexName).isAcknowledged());
        assertFalse(isIndexExist(indexName));
    }
}
