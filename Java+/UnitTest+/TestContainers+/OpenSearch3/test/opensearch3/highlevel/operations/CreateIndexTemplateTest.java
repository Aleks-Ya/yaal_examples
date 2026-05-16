package opensearch3.highlevel.operations;

import opensearch3.OpenSearchExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.PutIndexTemplateRequest;
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

}
