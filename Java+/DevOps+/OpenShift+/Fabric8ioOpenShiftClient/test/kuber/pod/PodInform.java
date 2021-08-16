package kuber.pod;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import kuber.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PodInform {

    @Test
    void podInform() throws InterruptedException {
        var podName = "pod-" + UUID.randomUUID();
        var containerName = "alpine";
        var image = "alpine:3";
        var pod = new PodBuilder()
                .withNewMetadata().withName(podName).endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(containerName)
                .withImage(image)
                .withCommand(List.of("sh", "-c", "while true; do echo 'Hello, World!'; sleep 1; done"))
                .endContainer()
                .endSpec()
                .build();
        var client = ClientFactory.devClient();
        var createdPod = client.pods().create(pod);
        client.pods().inform(new PodResourceEventHandler(podName));

        Thread.sleep(5000);

        assertTrue(client.pods().delete(createdPod));
        Thread.sleep(5000);
    }

    private static class PodResourceEventHandler implements ResourceEventHandler<Pod> {
        private final String podName;

        public PodResourceEventHandler(String podName) {
            this.podName = podName;
        }

        @Override
        public void onAdd(Pod obj) {
            var name = obj.getMetadata().getName();
            if (podName.equals(name)) {
                System.out.println("Pod added: " + name);
            } else {
                System.out.println("Added another pod: " + name);
            }
        }

        @Override
        public void onUpdate(Pod oldObj, Pod newObj) {
            var name = newObj.getMetadata().getName();
            if (podName.equals(name)) {
                System.out.println("Pod updated: " + name);
            } else {
                System.out.println("Updated another pod: " + name);
            }
        }

        @Override
        public void onDelete(Pod obj, boolean deletedFinalStateUnknown) {
            var name = obj.getMetadata().getName();
            if (podName.equals(name)) {
                System.out.println("Pod deleted: " + name);
            } else {
                System.out.println("Deleted another pod: " + name);
            }
        }
    }
}
