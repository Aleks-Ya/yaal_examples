package aws1.auth;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileCredentialsProviderTest {

    @Test
    void getCredentials() {
        var credentialsProvider = new ProfileCredentialsProvider();
        var credentials = credentialsProvider.getCredentials();
        assertThat(credentials).isNotNull();
        assertThat(credentials.getAWSAccessKeyId()).isNotNull();
        assertThat(credentials.getAWSSecretKey()).isNotNull();
    }

}
