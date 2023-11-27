package es7.testframework.index;

import es7.testframework.EsHelper;
import org.assertj.core.api.Assertions;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.test.ESIntegTestCase;
import org.elasticsearch.xcontent.XContentType;
import org.junit.Test;

import static org.elasticsearch.test.hamcrest.ElasticsearchAssertions.assertAcked;

public class CreateIndexRequestTest extends ESIntegTestCase {

    @Test
    public void createIndex() {
        var indexName = EsHelper.createRandomIndexName();
        assertFalse(EsHelper.isIndexExist(indexName, client()));

        createIndex(indexName);
        assertTrue(EsHelper.isIndexExist(indexName, client()));
    }

    @Test
    public void createIndexWithMapping() {
        var indexName = EsHelper.createRandomIndexName();
        assertFalse(EsHelper.isIndexExist(indexName, client()));

        var type = "t1";
        var expMappingSource = "{\"t1\":{\"properties\":{\"message\":{\"type\":\"text\"}}}}";

        assertAcked(prepareCreate(indexName).addMapping(type, expMappingSource, XContentType.JSON));
        assertTrue(EsHelper.isIndexExist(indexName, client()));

        try (var client = client()) {
            var getMappingsRequest = new GetMappingsRequest();
            var actMappings = client.admin().indices().getMappings(getMappingsRequest).actionGet().getMappings();
            Assertions.assertThat(actMappings).hasSize(1);

            var indexMappingMap = actMappings.get(indexName);
            var typeMappingMetadata = indexMappingMap.get(type);
            var actMappingSource = typeMappingMetadata.source().toString();

            Assertions.assertThat(actMappingSource).isEqualTo(expMappingSource);
        }
    }
}
