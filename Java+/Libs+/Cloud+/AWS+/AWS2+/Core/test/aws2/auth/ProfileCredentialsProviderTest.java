package aws2.auth;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileCredentialsProviderTest {

    @Test
    void resolveCredentials() {
        try (var credentialsProvider = ProfileCredentialsProvider.builder().build()) {
            var credentials = credentialsProvider.resolveCredentials();
            assertThat(credentials).isNotNull();
            assertThat(credentials.accessKeyId()).isNotNull();
            assertThat(credentials.secretAccessKey()).isNotNull();
        }
    }

}
