package elastic;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class Timeouts {
    @Test
    public void timeouts() throws IOException {
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
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

}
