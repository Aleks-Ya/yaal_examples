package kuber.configmap;

import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.PodBuilder;
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
                .withNewSpec()
                .addNewContainer()
                .withName(containerName)
                .withImage(image)
                .withCommand(List.of("sh", "-c", "ls -l /etc/cm; while true; do echo \"Hello, $(cat /etc/cm/name.txt) ($(cat /etc/cm/age.txt) years)!\"; sleep 3; done"))
                .withVolumeMounts(new VolumeMountBuilder()
                        .withName(volumeName)
                        .withMountPath("/etc/cm")
                        .build())
                .endContainer()
                .withVolumes(new VolumeBuilder()
                        .withName(volumeName)
                        .withNewConfigMap()
                        .withName(cmName)
                        .endConfigMap()
                        .build())
                .endSpec()
                .build();

        var client = ClientFactory.getDeveloperClient();
        var createdCm = client.configMaps().create(cm);
        var createdPod = client.pods().create(pod);

        assertTrue(client.pods().delete(createdPod));
        assertTrue(client.configMaps().delete(createdCm));
    }

}
