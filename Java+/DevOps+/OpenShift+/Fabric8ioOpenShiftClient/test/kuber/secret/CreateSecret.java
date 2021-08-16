package kuber.secret;

import io.fabric8.kubernetes.api.model.SecretBuilder;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import static kuber.ClientFactory.devClient;
import static kuber.ClientFactory.devHelper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateSecret {

    @Test
    void createSecret() {
        var usernameKey = "username";
        var passwordKey = "password";
        var usernameValue = Base64.getEncoder().encodeToString("admin".getBytes());
        var passwordValue = Base64.getEncoder().encodeToString("j8Nqie9".getBytes());

        var secretName = "secret-" + UUID.randomUUID();
        var secret = new SecretBuilder()
                .withNewMetadata().withName(secretName).endMetadata()
                .withData(Map.of(
                        usernameKey, usernameValue,
                        passwordKey, passwordValue))
                .build();
        var client = devClient();
        client.secrets().create(secret);

        var actSecret = devHelper().secretByName(secretName);
        assertThat(actSecret.getData().get(usernameKey), equalTo(usernameValue));

        assertTrue(client.secrets().delete(actSecret));
    }

}
