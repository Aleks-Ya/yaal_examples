package secretsmanager2;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

import static org.assertj.core.api.Assertions.assertThat;

class SecretsManagerTest {
    @Test
    void getSecret() {
        try (var client = SecretsManagerClient.create();) {
            var request = GetSecretValueRequest.builder().secretId("mysecret").build();
            var response = client.getSecretValue(request);
            var value = response.secretString();
            assertThat(value).isEqualTo("pass1");
        }
    }
}
