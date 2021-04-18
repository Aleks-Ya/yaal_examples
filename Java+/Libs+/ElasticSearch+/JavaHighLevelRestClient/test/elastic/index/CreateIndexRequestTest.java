package elastic.index;

import org.junit.jupiter.api.Test;

import static elastic.EsHelper.createIndex;
import static elastic.EsHelper.createRandomIndexName;
import static elastic.EsHelper.deleteIndex;
import static elastic.EsHelper.isIndexExist;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
