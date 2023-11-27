package es8.testframework.index;

import org.elasticsearch.test.ESIntegTestCase;
import org.junit.Test;

import static es8.testframework.EsHelper.createRandomIndexName;
import static es8.testframework.EsHelper.isIndexExist;

public class CreateIndexRequestTest extends ESIntegTestCase {
    @Test
    public void createIndex() {
        var indexName = createRandomIndexName();
        assertFalse(isIndexExist(indexName, client()));

        createIndex(indexName);
        assertTrue(isIndexExist(indexName, client()));
    }
}
