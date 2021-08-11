package kuber;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.junit.jupiter.api.Test;

class KuberClientTest {

    @Test
    void listPods() {
        KubernetesClient client = new DefaultKubernetesClient();
    }

}
