package elastic.document;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.test.ESIntegTestCase;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import static elastic.EsHelper.createRandomIndexName;
import static org.hamcrest.Matchers.equalTo;

public class IndexRequestTest extends ESIntegTestCase {

    @Test
    public void indexDocument() {
        var indexName = createRandomIndexName();
        createIndex(indexName);

        var client = client();

        var request = new IndexRequest(indexName);
        request.id("1");
        var jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);

        var indexResponse = client.index(request).actionGet();
        var status = indexResponse.status();
        var statusCode = status.getStatus();
        MatcherAssert.assertThat(statusCode, equalTo(201));
    }
}
