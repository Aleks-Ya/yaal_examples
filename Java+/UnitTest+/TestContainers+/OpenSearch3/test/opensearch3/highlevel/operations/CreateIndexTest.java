package opensearch3.highlevel.operations;

import opensearch3.OpenSearchExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.common.xcontent.XContentType;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.opensearch.client.RequestOptions.DEFAULT;

@ExtendWith(OpenSearchExtension.class)
class CreateIndexTest {

    @Test
    void createEmptyIndex(RestHighLevelClient client) throws IOException {
        var request = new CreateIndexRequest("index1");
        var response = client.indices().create(request, DEFAULT);
        assertThat(response.isAcknowledged()).isTrue();
    }

    @Test
    void createIndexWithJsonBody(RestHighLevelClient client) throws IOException {
        var request = new CreateIndexRequest("index2");
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
        request.source(jsonBody, XContentType.JSON);
        var response = client.indices().create(request, DEFAULT);
        assertThat(response.isAcknowledged()).isTrue();
    }

}
