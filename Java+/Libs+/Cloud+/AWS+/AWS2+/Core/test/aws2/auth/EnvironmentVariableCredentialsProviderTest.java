package aws2.auth;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;

import static org.assertj.core.api.Assertions.assertThat;

class EnvironmentVariableCredentialsProviderTest {

    @Test
    void resolveCredentials() throws Exception {
        var envs = new EnvironmentVariables();
        envs.setup();
        envs.set("AWS_ACCESS_KEY_ID", "key1");
        envs.set("AWS_SECRET_ACCESS_KEY", "secret1");
        var credentialsProvider = EnvironmentVariableCredentialsProvider.create();
        var credentials = credentialsProvider.resolveCredentials();
        assertThat(credentials).isNotNull();
        assertThat(credentials.accessKeyId()).isNotNull();
        assertThat(credentials.secretAccessKey()).isNotNull();
    }

}
