package kuber.pod;

import io.fabric8.kubernetes.api.model.PodBuilder;
import kuber.ClientFactory;
import kuber.RunningPodWatcher;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PodLogs {

    @Test
    void getPodLogs() {
        var podName = "pod-" + UUID.randomUUID();
        var containerName = "alpine";
        var image = "alpine:3";
        var pod = new PodBuilder()
                .withNewMetadata().withName(podName).endMetadata()
                .withNewSpec()
                .addNewContainer().withName(containerName).withImage(image).withCommand(List.of("sh", "-c", "while true; do echo 'Hello, World!'; sleep 1; done")).endContainer()
                .endSpec()
                .build();
        var client = ClientFactory.getDeveloperClient();
        var createdPod = client.pods().create(pod);
        var watcher = new RunningPodWatcher(podName);
        client.pods().watch(watcher);
        watcher.waitUntilIsRunning();
        var pp = client.pods().inNamespace(createdPod.getMetadata().getNamespace()).withName(podName);
        var logs = pp.getLog();
        System.out.println(logs);

        assertTrue(client.pods().delete(createdPod));
    }

}
