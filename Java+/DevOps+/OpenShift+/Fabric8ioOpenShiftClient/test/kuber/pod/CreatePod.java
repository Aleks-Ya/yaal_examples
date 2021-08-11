package kuber.pod;

import io.fabric8.kubernetes.api.model.PodBuilder;
import kuber.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreatePod {

    @Test
    void createPod() {
        var podName = "pod-" + UUID.randomUUID();
        var containerName = "alpine";
        var image = "alpine:3";
        var pod = new PodBuilder()
                .withNewMetadata().withName(podName).endMetadata()
                .withNewSpec()
                .addNewContainer().withName(containerName).withImage(image).withCommand(List.of("sh", "-c", "while true; do echo 'Hello, World!'; sleep 3; done")).endContainer()
                .endSpec()
                .build();
        var client = ClientFactory.getDeveloperClient();
        client.pods().create(pod);

        var podList = client.pods().list().getItems();
        var actPod = podList.stream().filter(map -> podName.equals(map.getMetadata().getName())).findFirst().orElseThrow();
        var container = actPod.getSpec().getContainers().stream().filter(c -> containerName.equals(c.getName())).findFirst().orElseThrow();
        assertThat(container.getImage(), equalTo(image));

        assertTrue(client.pods().delete(actPod));
    }

}
