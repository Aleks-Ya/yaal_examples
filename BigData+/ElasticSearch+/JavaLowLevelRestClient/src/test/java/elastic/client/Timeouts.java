package elastic.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Test;

import java.io.IOException;

import static elastic.client.SecurityHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class Timeouts {
    @Test
    public void timeouts() throws IOException {
        RestClient client = RestClient.builder(new HttpHost(HOST, PORT, SCHEMA))
                .setHttpClientConfigCallback(SecurityHelper.httpClientConfigCallback)
                .setRequestConfigCallback(
                        requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(60000)
                )
                .setMaxRetryTimeoutMillis(60000)
                .build();

        Response response = client.performRequest("GET", "/");
        client.close();

        System.out.println("Response: " + response);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

}
