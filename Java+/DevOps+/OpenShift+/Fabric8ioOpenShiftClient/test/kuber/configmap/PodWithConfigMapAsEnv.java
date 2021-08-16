package kuber.configmap;

import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.EnvFromSourceBuilder;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.PodSpecBuilder;
import kuber.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PodWithConfigMapAsEnv {

    @Test
    void createPodWithConfigMapAsEnv() {
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
                .withSpec(new PodSpecBuilder()
                        .withContainers(new ContainerBuilder()
                                .withName(containerName)
                                .withImage(image)
                                .withCommand(List.of("sh", "-c", "while true; do echo \"Hello, ${NAME} (${AGE} years)!\"; sleep 3; done"))
                                .withEnvFrom(new EnvFromSourceBuilder()
                                        .withNewConfigMapRef()
                                        .withName(cmName)
                                        .endConfigMapRef()
                                        .build())
                                .build())
                        .build())
                .build();

        var client = ClientFactory.devClient();
        client.configMaps().create(cm);
        client.pods().create(pod);

        assertTrue(client.pods().delete(pod));
        assertTrue(client.configMaps().delete(cm));
    }

}
