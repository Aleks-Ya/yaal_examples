package elastic.index;

import org.elasticsearch.test.ESIntegTestCase;
import org.junit.Test;

import static elastic.EsHelper.createRandomIndexName;
import static elastic.EsHelper.isIndexExist;

public class CreateIndexRequestTest extends ESIntegTestCase {
    @Test
    void createIndex() {
        var indexName = createRandomIndexName();
        assertFalse(isIndexExist(indexName, client()));

        createIndex(indexName);
        assertTrue(isIndexExist(indexName, client()));
    }
}
