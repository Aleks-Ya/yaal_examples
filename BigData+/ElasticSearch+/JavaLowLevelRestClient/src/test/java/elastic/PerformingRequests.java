package elastic;

import org.apache.http.HttpEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PerformingRequests {
    private static final String PEOPLE_INDEX = "people";
    private static final String PERSONS_TYPE = "persons";
    private static final String EMAIL_FILED = "email";
    private static final String JOHN_EMAIL = "john@mail.ru";
    private static final String MARY_MAIL = "mary@mail.ru";
    private final RestClient client = SecurityHelper.getLowLevelRestClient();

    @Test
    public void getRoot() throws IOException {
        Response response = client.performRequest("GET", "/");
        client.close();

        System.out.println("Response: " + response);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public void getMapping() throws IOException {
        Response response = client.performRequest("GET", "/_mapping");
        client.close();
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

        System.out.println("Response: " + response);
        HttpEntity entity = response.getEntity();
        InputStream contentIS = entity.getContent();
        String contentStr = StringHelper.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
    }

    @Test
    public void count() throws IOException {
        String endpoint = format("%s/%s/_count", PEOPLE_INDEX, PERSONS_TYPE);
        Response response = client.performRequest("GET", endpoint);
        client.close();
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

        System.out.println("Response: " + response);
        HttpEntity entity = response.getEntity();
        InputStream contentIS = entity.getContent();
        String contentStr = StringHelper.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
        assertThat(contentStr, containsString("\"total\":5"));
    }

}
