package elastic.index;

import org.assertj.core.api.Assertions;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.test.ESIntegTestCase;
import org.elasticsearch.xcontent.XContentType;
import org.junit.Test;

import static elastic.EsHelper.createRandomIndexName;
import static elastic.EsHelper.isIndexExist;
import static org.elasticsearch.test.hamcrest.ElasticsearchAssertions.assertAcked;

public class CreateIndexRequestTest extends ESIntegTestCase {

    @Test
    void createIndex() {
        var indexName = createRandomIndexName();
        assertFalse(isIndexExist(indexName, client()));

        createIndex(indexName);
        assertTrue(isIndexExist(indexName, client()));
    }

    @Test
    void createIndexWithMapping() {
        var indexName = createRandomIndexName();
        assertFalse(isIndexExist(indexName, client()));

        var type = "t1";
        var expMappingSource = "{\"t1\":{\"properties\":{\"message\":{\"type\":\"text\"}}}}";

        assertAcked(prepareCreate(indexName).addMapping(type, expMappingSource, XContentType.JSON));
        assertTrue(isIndexExist(indexName, client()));

        Client client = client();
        var getMappingsRequest = new GetMappingsRequest();
        var actMappings = client.admin().indices().getMappings(getMappingsRequest).actionGet().getMappings();
        Assertions.assertThat(actMappings).hasSize(1);

        var indexMappingMap = actMappings.get(indexName);
        var typeMappingMetadata = indexMappingMap.get(type);
        var actMappingSource = typeMappingMetadata.source().toString();

        Assertions.assertThat(actMappingSource).isEqualTo(expMappingSource);
    }
}
