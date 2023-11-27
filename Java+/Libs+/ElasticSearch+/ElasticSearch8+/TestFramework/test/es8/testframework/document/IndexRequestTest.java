package es8.testframework.document;

import org.assertj.core.api.Assertions;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.test.ESIntegTestCase;
import org.elasticsearch.xcontent.XContentType;
import org.junit.Test;

import static es8.testframework.EsHelper.createRandomIndexName;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class IndexRequestTest extends ESIntegTestCase {
    @Test
    public void indexDocument() {
        var indexName = createRandomIndexName();
        createIndex(indexName);
        try (var client = client()) {
            var indexRequest = new IndexRequest(indexName);
            var id = "1";
            indexRequest.id(id);
            var jsonString = "{" +
                             "\"user\":\"John\"," +
                             "\"postDate\":\"2021-01-30\"," +
                             "\"message\":\"trying out ES\"" +
                             "}";
            indexRequest.source(jsonString, XContentType.JSON);

            var indexResponse = client.index(indexRequest).actionGet();
            var status = indexResponse.status();
            var statusCode = status.getStatus();
            Assertions.assertThat(statusCode).isEqualTo(201);

            var getRequest = new GetRequest(indexName, id);
            var getResponse = client.get(getRequest).actionGet();
            var source = getResponse.getSourceAsString();
            assertThatJson(source).isEqualTo("{postDate: 2021-01-30, message: 'trying out ES', user: John}");
        }
    }
}
