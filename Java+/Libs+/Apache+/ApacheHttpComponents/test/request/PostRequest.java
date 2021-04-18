package request;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PostRequest {

    @Test
    public void post() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ContentType contentType = ContentType.create("text/plain", "UTF-8");
            StringEntity myEntity = new StringEntity("important message", contentType);
            HttpPost httpPost = new HttpPost("http://httpbin.org/post");
            httpPost.setEntity(myEntity);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                assertThat(response.getCode(), equalTo(200));
            }
        }
    }

}