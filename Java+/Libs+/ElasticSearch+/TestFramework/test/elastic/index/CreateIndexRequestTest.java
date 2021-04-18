package elastic.index;

import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.test.ESIntegTestCase;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static elastic.EsHelper.createRandomIndexName;
import static elastic.EsHelper.isIndexExist;
import static org.elasticsearch.test.hamcrest.ElasticsearchAssertions.assertAcked;
import static org.hamcrest.Matchers.equalTo;

public class CreateIndexRequestTest extends ESIntegTestCase {

    @Test
    public void createIndex() {
        var indexName = createRandomIndexName();
        assertFalse(isIndexExist(indexName, client()));

        createIndex(indexName);
        assertTrue(isIndexExist(indexName, client()));
    }

    @Test
    public void createIndexWithMapping() {
        var indexName = createRandomIndexName();
        assertFalse(isIndexExist(indexName, client()));

        var type = "t1";
        var expMappingSource = "{\"t1\":{\"properties\":{\"message\":{\"type\":\"text\"}}}}";

        assertAcked(prepareCreate(indexName).addMapping(type, expMappingSource, XContentType.JSON));
        assertTrue(isIndexExist(indexName, client()));

        Client client = client();
        var getMappingsRequest = new GetMappingsRequest();
        var actMappings = client.admin().indices().getMappings(getMappingsRequest).actionGet().getMappings();
        MatcherAssert.assertThat(actMappings.size(), equalTo(1));

        var indexMappingMap = actMappings.get(indexName);
        var typeMappingMetadata = indexMappingMap.get(type);
        var actMappingSource = typeMappingMetadata.source().toString();

        MatcherAssert.assertThat(actMappingSource, equalTo(expMappingSource));
    }
}
