package kuber.pod;

import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.PodSpecBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static kuber.ClientFactory.devClient;
import static kuber.ClientFactory.devHelper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PodLogs {

    @Test
    void getPodLogs() {
        var podName = "pod-" + UUID.randomUUID();
        var greeting = "Hello, Log!";
        var pod = new PodBuilder()
                .withNewMetadata().withName(podName).endMetadata()
                .withSpec(new PodSpecBuilder()
                        .withContainers(new ContainerBuilder()
                                .withName("alpine")
                                .withImage("alpine:3")
                                .withCommand(List.of("echo", greeting))
                                .build())
                        .build())
                .build();
        var client = devClient();
        var createdPod = client.pods().create(pod);
        devHelper().waitUntilRunning(podName);
        var logs = devHelper().podLogs(podName);
        System.out.println(logs);
        assertTrue(client.pods().delete(createdPod));

        assertThat(logs).contains(greeting);
    }

}
