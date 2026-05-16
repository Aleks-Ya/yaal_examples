package opensearch3.lowlevel;

import org.apache.hc.core5.http.HttpHost;
import org.junit.jupiter.api.Test;
import org.opensearch.client.Request;
import org.opensearch.client.RestClient;
import org.opensearch.testcontainers.OpenSearchContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.net.URISyntaxException;

import static opensearch3.OpenSearchExtension.OPENSEARCH3;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class RestClientTest {

    @Test
    void initRestClient() throws IOException, URISyntaxException {
        try (var container = new OpenSearchContainer<>(OPENSEARCH3)) {
            container.start();
            try (var client = RestClient.builder(HttpHost.create(container.getHttpHostAddress())).build()) {
                var response = client.performRequest(new Request("GET", "/_cluster/health"));
                assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
            }
        }
    }

}
