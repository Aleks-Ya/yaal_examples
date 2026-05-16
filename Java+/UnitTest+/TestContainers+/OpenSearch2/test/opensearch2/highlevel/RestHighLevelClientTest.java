package opensearch2.highlevel;

import org.apache.http.HttpHost;
import org.junit.jupiter.api.Test;
import org.opensearch.action.admin.cluster.health.ClusterHealthRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.cluster.health.ClusterHealthStatus;
import org.opensearch.testcontainers.OpenSearchContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static opensearch2.OpenSearchExtension.OPENSEARCH2;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class RestHighLevelClientTest {

    @Test
    void initHighLevelClient() throws IOException {
        try (var container = new OpenSearchContainer<>(OPENSEARCH2)) {
            container.start();
            var builder = RestClient.builder(HttpHost.create(container.getHttpHostAddress()));
            try (var client = new RestHighLevelClient(builder)) {
                var request = new ClusterHealthRequest();
                var response = client.cluster().health(request, RequestOptions.DEFAULT);
                assertThat(response.getStatus()).isEqualTo(ClusterHealthStatus.GREEN);
            }
        }
    }

}
