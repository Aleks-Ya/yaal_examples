package es8.low;

import es8.low.SecurityHelper;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import util.InputStreamUtil;
import util.RandomUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

class PerformingRequestsTest {
    private static final String PEOPLE_INDEX = "people";
    private static final String PERSONS_TYPE = "persons";
    private final RestClient client = SecurityHelper.getLowLevelRestClient();

    @Test
    void getRoot() throws IOException {
        var request = new Request("GET", "/");
        var response = client.performRequest(request);
        client.close();

        System.out.println("Response: " + response);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
    }

    @Test
    void getMapping() throws IOException {
        var request = new Request("GET", "/_mapping");
        var response = client.performRequest(request);
        client.close();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);

        System.out.println("Response: " + response);
        var entity = response.getEntity();
        var contentIS = entity.getContent();
        var contentStr = InputStreamUtil.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
    }

    @Test
    void countAll() throws IOException {
        var endpoint = format("%s/%s/_count", PEOPLE_INDEX, PERSONS_TYPE);
        var request = new Request("GET", endpoint);
        var response = client.performRequest(request);
        client.close();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);

        System.out.println("Response: " + response);
        var entity = response.getEntity();
        var contentIS = entity.getContent();
        var contentStr = InputStreamUtil.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
        assertThat(contentStr).containsSubsequence("\"count\":4");
    }

    @Test
    void countSearch() throws IOException {
        var endpoint = format("%s/%s/_count", PEOPLE_INDEX, PERSONS_TYPE);
        Map<String, String> params = Collections.emptyMap();
        var body = "{\n" +
                "  \"query\": {\n" +
                "      \"term\": {\n" +
                "          \"email\": \"john@mail.ru\"\n" +
                "      }\n" +
                "  }\n" +
                "}";
        HttpEntity requestEntity = new NStringEntity(body, ContentType.APPLICATION_JSON);
        var request = new Request("GET", endpoint);
        request.addParameters(params);
        request.setEntity(requestEntity);
        var response = client.performRequest(request);
        client.close();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);

        System.out.println("Response: " + response);
        var responseEntity = response.getEntity();
        var contentIS = responseEntity.getContent();
        var contentStr = InputStreamUtil.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
        assertThat(contentStr).containsSubsequence("\"count\":1");
    }

    @Test
    void createIndex() throws IOException {
        var indexName = RandomUtil.randomIntPositive();
        var request = new Request("PUT", "/" + indexName);
        var response = client.performRequest(request);
        client.close();
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);

        System.out.println("Response: " + response);
        var entity = response.getEntity();
        var contentIS = entity.getContent();
        var contentStr = InputStreamUtil.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
        assertThat(contentStr).containsSubsequence("\"acknowledged\":true");
    }

}
