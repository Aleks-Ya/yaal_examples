package es7.high.document;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static es7.high.EsHelper.createIndex;
import static es7.high.EsHelper.createRandomIndexName;
import static es7.high.EsHelper.deleteIndex;
import static es7.high.EsHelper.getHighLevelRestClient;
import static org.assertj.core.api.Assertions.assertThat;

class IndexRequestTest {

    @Test
    void indexDocument() throws IOException {
        var indexName = createRandomIndexName();
        try {
            createIndex(indexName);

            var request = new IndexRequest(indexName);
            request.id("1");
            var jsonString = "{" +
                    "\"user\":\"kimchy\"," +
                    "\"postDate\":\"2013-01-30\"," +
                    "\"message\":\"trying out Elasticsearch\"" +
                    "}";
            request.source(jsonString, XContentType.JSON);

            var client = getHighLevelRestClient();
            var indexResponse = client.index(request, RequestOptions.DEFAULT);
            var status = indexResponse.status();
            var statusCode = status.getStatus();
            assertThat(statusCode).isEqualTo(201);
        } finally {
            deleteIndex(indexName);
        }
    }
}
