package aws2.auth;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static software.amazon.awssdk.core.SdkSystemSetting.AWS_ACCESS_KEY_ID;
import static software.amazon.awssdk.core.SdkSystemSetting.AWS_SECRET_ACCESS_KEY;

class SystemPropertyCredentialsProviderTest {

    @Test
    void resolveCredentials() {
        System.setProperty(AWS_ACCESS_KEY_ID.property(), "key1");
        System.setProperty(AWS_SECRET_ACCESS_KEY.property(), "secret1");
        var credentialsProvider = SystemPropertyCredentialsProvider.create();
        var credentials = credentialsProvider.resolveCredentials();
        assertThat(credentials).isNotNull();
        assertThat(credentials.accessKeyId()).isNotNull();
        assertThat(credentials.secretAccessKey()).isNotNull();
    }

}
