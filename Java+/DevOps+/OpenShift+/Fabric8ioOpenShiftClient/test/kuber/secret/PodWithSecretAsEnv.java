package kuber.secret;

import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.EnvFromSourceBuilder;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.PodSpecBuilder;
import io.fabric8.kubernetes.api.model.SecretBuilder;
import io.fabric8.kubernetes.api.model.SecretEnvSourceBuilder;
import kuber.ClientFactory;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static kuber.ClientFactory.devHelper;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PodWithSecretAsEnv {

    @Test
    void createPodWithSecretAsEnv() {
        var username = "admin";
        var password = "j8Nqie9";
        var usernameKey = "username";
        var passwordKey = "password";
        var usernameValue = Base64.getEncoder().encodeToString(username.getBytes());
        var passwordValue = Base64.getEncoder().encodeToString(password.getBytes());

        var secretName = "secret-" + UUID.randomUUID();
        var secret = new SecretBuilder()
                .withNewMetadata().withName(secretName).endMetadata()
                .withData(Map.of(
                        usernameKey, usernameValue,
                        passwordKey, passwordValue))
                .build();
        var client = ClientFactory.devClient();
        var createdSecret = client.secrets().create(secret);


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
                                .withCommand(List.of("sh", "-c", "echo Hello, ${username} with ${password}!"))
                                .withEnvFrom(new EnvFromSourceBuilder()
                                        .withSecretRef(new SecretEnvSourceBuilder()
                                                .withName(secretName)
                                                .build())
                                        .build())
                                .build())
                        .build())
                .build();
        var createdPod = client.pods().create(pod);

        devHelper().waitUntilRunning(podName);
        var logs = devHelper().podLogs(podName);
        System.out.println(logs);

        assertTrue(client.pods().delete(createdPod));
        assertTrue(client.secrets().delete(createdSecret));

        assertThat(logs).contains(format("Hello, %s with %s!", username, password));
    }

}
