package kuber.configmap;

import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ConfigMapVolumeSourceBuilder;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.PodSpecBuilder;
import io.fabric8.kubernetes.api.model.VolumeBuilder;
import io.fabric8.kubernetes.api.model.VolumeMountBuilder;
import kuber.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PodWithConfigMapAsFile {

    @Test
    void createPodWithConfigMapAsFile() {
        var personFile = "name.txt";
        var ageFile = "age.txt";
        var cmName = "cm-" + UUID.randomUUID();
        System.out.println("CM: " + cmName);
        var cm = new ConfigMapBuilder()
                .withNewMetadata().withName(cmName).endMetadata()
                .withData(Map.of(personFile, "John", ageFile, "30"))
                .build();

        var podName = "pod-" + UUID.randomUUID();
        System.out.println("Pod: " + podName);
        var containerName = "alpine";
        var image = "alpine:3";
        var volumeName = "cm-as-file-volume";
        var pod = new PodBuilder()
                .withNewMetadata().withName(podName).endMetadata()
                .withSpec(new PodSpecBuilder()
                        .withContainers(new ContainerBuilder()
                                .withName(containerName)
                                .withImage(image)
                                .withCommand(List.of("sh", "-c", "ls -l /etc/cm; while true; do echo \"Hello, $(cat /etc/cm/name.txt) ($(cat /etc/cm/age.txt) years)!\"; sleep 3; done"))
                                .withVolumeMounts(new VolumeMountBuilder()
                                        .withName(volumeName)
                                        .withMountPath("/etc/cm")
                                        .build())
                                .build())
                        .withVolumes(new VolumeBuilder()
                                .withName(volumeName)
                                .withConfigMap(new ConfigMapVolumeSourceBuilder()
                                        .withName(cmName)
                                        .build())
                                .build())
                        .build())
                .build();

        var client = ClientFactory.devClient();
        var createdCm = client.configMaps().create(cm);
        var createdPod = client.pods().create(pod);

        assertTrue(client.pods().delete(createdPod));
        assertTrue(client.configMaps().delete(createdCm));
    }

}
