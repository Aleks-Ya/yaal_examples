package opensearch3.lowlevel.operations;

import opensearch3.OpenSearchExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opensearch.client.Request;
import org.opensearch.client.RestClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OpenSearchExtension.class)
class ClusterHealthTest {

    @Test
    void getClusterHealth(RestClient client) throws IOException {
        var response = client.performRequest(new Request("GET", "/_cluster/health"));
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
        try (var is = response.getEntity().getContent()) {
            var content = new String(is.readAllBytes());
            System.out.println(content);
        }
    }

}
