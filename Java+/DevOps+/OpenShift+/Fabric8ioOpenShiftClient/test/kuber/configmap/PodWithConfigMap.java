package kuber.configmap;

import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.EnvFromSourceBuilder;
import io.fabric8.kubernetes.api.model.PodBuilder;
import kuber.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PodWithConfigMap {

    @Test
    void createPodWithConfigMap() {
        var dataName = "NAME";
        var dataAge = "AGE";
        var cmName = "cm-" + UUID.randomUUID();
        System.out.println("CM: " + cmName);
        var cm = new ConfigMapBuilder()
                .withNewMetadata().withName(cmName).endMetadata()
                .withData(Map.of(dataName, "John", dataAge, "30"))
                .build();

        var podName = "pod-" + UUID.randomUUID();
        System.out.println("Pod: " + podName);
        var containerName = "alpine";
        var image = "alpine:3";
        var pod = new PodBuilder()
                .withNewMetadata().withName(podName).endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(containerName)
                .withImage(image)
                .withCommand(List.of("sh", "-c", "while true; do echo \"Hello, ${NAME} (${AGE} years)!\"; sleep 3; done"))
                .withEnvFrom(new EnvFromSourceBuilder()
                        .withNewConfigMapRef()
                        .withName(cmName)
                        .endConfigMapRef()
                        .build())
                .endContainer()
                .endSpec()
                .build();

        var client = ClientFactory.getDeveloperClient();
        client.configMaps().create(cm);
        client.pods().create(pod);

        assertTrue(client.pods().delete(pod));
        assertTrue(client.configMaps().delete(cm));
    }

}
