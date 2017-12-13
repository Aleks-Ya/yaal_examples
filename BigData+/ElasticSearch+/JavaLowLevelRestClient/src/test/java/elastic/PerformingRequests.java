package elastic;

import org.apache.http.HttpEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class PerformingRequests {

    @Test
    public void getRoot() throws IOException {
        RestClient client = SecurityHelper.getLowLevelRestClient();

        Response response = client.performRequest("GET", "/");
        client.close();

        System.out.println("Response: " + response);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    public void getMapping() throws IOException {
        RestClient client = SecurityHelper.getLowLevelRestClient();

        Response response = client.performRequest("GET", "/_mapping");
        client.close();
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

        System.out.println("Response: " + response);
        HttpEntity entity = response.getEntity();
        InputStream contentIS = entity.getContent();
        String contentStr = StringHelper.inputStreamToString(contentIS);
        System.out.println("Body: " + contentStr);
    }

}
