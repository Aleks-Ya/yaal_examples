package opensearch3.highlevel.operations;

import opensearch3.OpenSearchExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.opensearch.OpenSearchStatusException;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.ResponseException;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.opensearch.client.RequestOptions.DEFAULT;
import static org.opensearch.common.xcontent.XContentType.JSON;

class HttpMaxContentLengthTest {
    @RegisterExtension
    static final OpenSearchExtension OPEN_SEARCH = new OpenSearchExtension()
            .withEnv("http.max_content_length", "1kb");

    @Test
    void exceedMaxContentLength(RestHighLevelClient client) throws IOException {
        var index = "index1";

        client.indices().create(new CreateIndexRequest(index), DEFAULT);

        var id = "1";
        var largeJson = """
                {"title": "Document Title", "content": "%s"}""".formatted("abc ".repeat(300));
        assertThat(largeJson).hasSize(1242);
        var indexRequest = new IndexRequest(index).id(id).source(largeJson, JSON);
        assertThatThrownBy(() -> client.index(indexRequest, DEFAULT))
                .isInstanceOf(OpenSearchStatusException.class)
                .cause()
                .isInstanceOf(ResponseException.class)
                .hasMessageContaining("413 Request Entity Too Large");
    }

}
