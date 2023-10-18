package elastic;

import org.elasticsearch.client.Request;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class TimeoutTest {
    @Test
    void timeouts() throws IOException {
        var client = SecurityHelper.newRestClientBuilder()
                .setRequestConfigCallback(
                        requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(60000)
                )
//                .setMaxRetryTimeoutMillis(60000)
                .build();

        var request = new Request("GET", "/");
        var response = client.performRequest(request);
        client.close();

        System.out.println("Response: " + response);
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
    }

}
