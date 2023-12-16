package gcp.ai;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.aiplatform.v1.ModelName;
import com.google.cloud.aiplatform.v1.ModelServiceClient;
import com.google.cloud.aiplatform.v1.ModelServiceSettings;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.google.auth.oauth2.GoogleCredentials.fromStream;
import static org.assertj.core.api.Assertions.assertThat;
import static util.FileUtil.homeDirFileToIS;

/**
 * DOES NOT WORK
 */
class GetModelTest {
    @Test
    void get() throws IOException {
        var credentials = fromStream(homeDirFileToIS(".gcp-client", "api-client-408303-28254c1c5a46.json"));
        var credentialsProvider = FixedCredentialsProvider.create(credentials);
        var serviceSettings = ModelServiceSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
        try (var modelServiceClient = ModelServiceClient.create(serviceSettings)) {
            var modelName = ModelName.of("api-client-408303", "us-central1", "gemini-pro");
            var model = modelServiceClient.getModel(modelName);
            assertThat(model).isNotNull();
        }
    }
}
