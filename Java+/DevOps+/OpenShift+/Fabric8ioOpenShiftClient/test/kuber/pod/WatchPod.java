package kuber.pod;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import kuber.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class WatchPod {

    @Test
    void watchPod() {
        var namespace = "os-client";
        var podName = "pod-" + UUID.randomUUID();
        var containerName = "alpine";
        var image = "alpine:3";
        var pod = new PodBuilder()
                .withNewMetadata().withName(podName).withNamespace(namespace).endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(containerName)
                .withImage(image)
                .withCommand(List.of("sh", "-c", "while true; do echo 'Hello, World!'; sleep 1; done"))
                .endContainer()
                .endSpec()
                .build();
        System.out.println("Pod: " + podName);
        var client = ClientFactory.getDeveloperClient();
        var createdPod = client.pods().create(pod);
        var watcher = new RunningPodWatcher();
        client.pods().watch(watcher);
        watcher.waitUntilIsRunning();
        System.out.println("Pod is running!");
        assertTrue(client.pods().delete(createdPod));
    }

    private static class RunningPodWatcher implements Watcher<Pod> {
        private boolean isRunning = false;

        @Override
        public void eventReceived(Action action, Pod resource) {
            System.out.println("Action: " + action);
            System.out.println("Phase: " + resource.getStatus().getPhase());
            isRunning = "Running".equals(resource.getStatus().getPhase());
        }

        @Override
        public void onClose(WatcherException cause) {
            System.out.println("Watcher exception: " + cause);
        }

        public void waitUntilIsRunning() {
            while (!isRunning) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
