package opensearch3.highlevel.operations;

import opensearch3.OpenSearchExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opensearch.action.admin.cluster.health.ClusterHealthRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.cluster.health.ClusterHealthStatus;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OpenSearchExtension.class)
class ClusterHealthTest {

    @Test
    void getClusterHealth(RestHighLevelClient client) throws IOException {
        var request = new ClusterHealthRequest();
        var response = client.cluster().health(request, RequestOptions.DEFAULT);
        assertThat(response.getStatus()).isEqualTo(ClusterHealthStatus.GREEN);
    }

}
