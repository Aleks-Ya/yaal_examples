package opensearch;

import org.apache.http.HttpHost;
import org.junit.jupiter.api.Test;
import org.opensearch.client.Request;
import org.opensearch.client.RestClient;
import org.opensearch.testcontainers.OpenSearchContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class OpenSearchTest {
    private static final DockerImageName OPENSEARCH_IMAGE = DockerImageName.parse("opensearchproject/opensearch:2.19.3");

    @Test
    void getClusterHealth() throws IOException {
        try (var container = new OpenSearchContainer<>(OPENSEARCH_IMAGE)) {
            container.start();
            try (var client = RestClient
                    .builder(HttpHost.create(container.getHttpHostAddress()))
                    .build()) {

                var response = client.performRequest(new Request("GET", "/_cluster/health"));
                assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
                try (var is = response.getEntity().getContent()) {
                    var content = new String(is.readAllBytes());
                    System.out.println(content);
                }
            }
        }
    }
}
