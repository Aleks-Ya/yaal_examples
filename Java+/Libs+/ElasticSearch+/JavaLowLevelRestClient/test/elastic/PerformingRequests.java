package elastic;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PerformingRequests {
    private static final String PEOPLE_INDEX = "people";
    private static final String PERSONS_TYPE = "persons";
    private final RestClient client = SecurityHelper.getLowLevelRestClient();

    @Test
    public void getRoot() throws IOException {
        var request = new Request("GET", "/");
        var response = client.performRequest(request);
        client.close();

        System.out.println("Response: " + response);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public void getMapping() throws IOException {
        var request = new Request("GET", "/_mapping");
        var response = client.performRequest(request);
        client.close();
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

        System.out.println("Response: " + response);
        var entity = response.getEntity();
        var contentIS = entity.getContent();
        var contentStr = StringHelper.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
    }

    @Test
    public void countAll() throws IOException {
        var endpoint = format("%s/%s/_count", PEOPLE_INDEX, PERSONS_TYPE);
        var request = new Request("GET", endpoint);
        var response = client.performRequest(request);
        client.close();
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

        System.out.println("Response: " + response);
        var entity = response.getEntity();
        var contentIS = entity.getContent();
        var contentStr = StringHelper.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
        assertThat(contentStr, containsString("\"count\":4"));
    }

    @Test
    public void countSearch() throws IOException {
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
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

        System.out.println("Response: " + response);
        var responseEntity = response.getEntity();
        var contentIS = responseEntity.getContent();
        var contentStr = StringHelper.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
        assertThat(contentStr, containsString("\"count\":1"));
    }

}