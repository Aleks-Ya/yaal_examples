package request;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PostRequest {

    @Test
    public void post() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ContentType contentType = ContentType.create("text/plain", "UTF-8");
            StringEntity myEntity = new StringEntity("important message", contentType);
            HttpPost httpPost = new HttpPost("http://httpbin.org/post");
            httpPost.setEntity(myEntity);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
            }
        }
    }

}