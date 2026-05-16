package opensearch2.lowlevel;

import org.apache.http.HttpHost;
import org.junit.jupiter.api.Test;
import org.opensearch.client.Request;
import org.opensearch.client.RestClient;
import org.opensearch.testcontainers.OpenSearchContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static opensearch2.OpenSearchExtension.OPENSEARCH2;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class RestClientTest {

    @Test
    void initRestClient() throws IOException {
        try (var container = new OpenSearchContainer<>(OPENSEARCH2)) {
            container.start();
            try (var client = RestClient.builder(HttpHost.create(container.getHttpHostAddress())).build()) {
                var response = client.performRequest(new Request("GET", "/_cluster/health"));
                assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
            }
        }
    }

}
