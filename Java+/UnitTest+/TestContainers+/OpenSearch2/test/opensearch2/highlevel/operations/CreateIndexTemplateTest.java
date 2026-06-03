package opensearch2.highlevel.operations;

import opensearch2.OpenSearchExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.PutComponentTemplateRequest;
import org.opensearch.client.indices.PutComposableIndexTemplateRequest;
import org.opensearch.client.indices.PutIndexTemplateRequest;
import org.opensearch.cluster.metadata.ComponentTemplate;
import org.opensearch.cluster.metadata.ComposableIndexTemplate;
import org.opensearch.cluster.metadata.Template;
import org.opensearch.common.compress.CompressedXContent;
import org.opensearch.core.xcontent.MediaTypeRegistry;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.opensearch.client.RequestOptions.DEFAULT;

@ExtendWith(OpenSearchExtension.class)
class CreateIndexTemplateTest {

    @Test
    void createEmptyTemplate(RestHighLevelClient client) throws IOException {
        var request = new PutIndexTemplateRequest("template1");
        request.patterns(Collections.singletonList("index1*"));
        var response = client.indices().putTemplate(request, DEFAULT);
        assertThat(response.isAcknowledged()).isTrue();
    }

    @Test
    void createTemplateWithJsonBody(RestHighLevelClient client) throws IOException {
        var request = new PutIndexTemplateRequest("template2");
        request.patterns(Collections.singletonList("index2*"));
        var jsonBody = """
                {
                  "settings": {
                    "number_of_shards": 1,
                    "number_of_replicas": 0
                  },
                  "mappings": {
                    "properties": {
                      "title": {
                        "type": "text"
                      },
                      "age": {
                        "type": "integer"
                      }
                    }
                  }
                }
                """;
        request.source(jsonBody, MediaTypeRegistry.JSON);
        var response = client.indices().putTemplate(request, DEFAULT);
        assertThat(response.isAcknowledged()).isTrue();
    }

    @Test
    void createTemplateWithComponentTemplate(RestHighLevelClient client) throws IOException {
        var mappings = new CompressedXContent("""
                {
                      "properties": {
                        "timestamp": {
                          "type": "date"
                        }
                      }
                    }
                """);
        var template = new Template(null, mappings, Collections.emptyMap());
        var componentTemplate = new ComponentTemplate(template, null, Collections.emptyMap());

        var componentRequest = new PutComponentTemplateRequest();
        componentRequest.name("component_template1");
        componentRequest.componentTemplate(componentTemplate);

        var componentResponse = client.cluster().putComponentTemplate(componentRequest, DEFAULT);
        assertThat(componentResponse.isAcknowledged()).isTrue();

        var indexTemplate = new ComposableIndexTemplate(
                Collections.singletonList("index3*"),
                null,
                Collections.singletonList("component_template1"),
                100L,
                null,
                Collections.emptyMap()
        );

        var templateRequest = new PutComposableIndexTemplateRequest();
        templateRequest.name("template3");
        templateRequest.indexTemplate(indexTemplate);

        var templateResponse = client.indices().putIndexTemplate(templateRequest, DEFAULT);
        assertThat(templateResponse.isAcknowledged()).isTrue();
    }

}
