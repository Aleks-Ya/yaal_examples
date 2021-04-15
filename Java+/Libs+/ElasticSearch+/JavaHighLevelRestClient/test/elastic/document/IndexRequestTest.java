package elastic.document;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.io.IOException;

import static elastic.ConnectionHelper.createIndex;
import static elastic.ConnectionHelper.createRandomIndexName;
import static elastic.ConnectionHelper.deleteIndex;
import static elastic.ConnectionHelper.getHighLevelRestClient;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IndexRequestTest {

    @Test
    public void indexDocument() throws IOException {
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
            assertThat(statusCode, equalTo(201));
        } finally {
            deleteIndex(indexName);
        }
    }
}
