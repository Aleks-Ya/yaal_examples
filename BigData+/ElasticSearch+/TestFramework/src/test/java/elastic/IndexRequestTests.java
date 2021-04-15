package elastic;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class IndexRequestTests extends ESIntegTestCase {

    @Test
    public void indexDocument() {
        String indexName = "test";
        createIndex(indexName);

        Client client = client();

        IndexRequest request = new IndexRequest(indexName);
        request.id("1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);

        IndexResponse indexResponse = client.index(request).actionGet();
        RestStatus status = indexResponse.status();
        int statusCode = status.getStatus();
        assertThat(statusCode, equalTo(201));
    }
}
