package request;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class PostRequestIT {

    @Test
    void post() throws IOException {
        try (var httpClient = HttpClients.createDefault()) {
            var contentType = ContentType.create("text/plain", "UTF-8");
            var entity = new StringEntity("important message", contentType);
            var httpPost = new HttpPost("https://httpbin.org/post");
            httpPost.setEntity(entity);
            try (var response = httpClient.execute(httpPost, r -> r)) {
                assertThat(response.getCode()).isEqualTo(200);
            }
        }
    }

}