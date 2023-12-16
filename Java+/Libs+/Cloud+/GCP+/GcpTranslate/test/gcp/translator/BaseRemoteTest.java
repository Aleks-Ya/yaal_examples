package gcp.translator;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.cloud.translate.v3.LocationName;
import com.google.cloud.translate.v3.TranslationServiceClient;
import com.google.cloud.translate.v3.TranslationServiceSettings;
import org.junit.jupiter.api.AfterAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.google.auth.oauth2.GoogleCredentials.fromStream;
import static util.FileUtil.homeDirFileToIS;

public abstract class BaseRemoteTest {
    protected static final TranslationServiceClient client = getClient();
    protected static final String parent = getParentLocationName();

    private static TranslationServiceClient getClient() {
        try {
            var credentials = fromStream(homeDirFileToIS(".gcp-client", "translator-api.json"));
            var credentialsProvider = FixedCredentialsProvider.create(credentials);
            var serviceSettings = TranslationServiceSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
            return TranslationServiceClient.create(serviceSettings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getParentLocationName() {
        try {
            var projectIdPath = Paths.get(System.getProperty("user.home"), ".gcp-client/project-id.txt");
            var projectId = Files.readString(projectIdPath);
            return LocationName.of(projectId, "global").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void afterAll() {
        client.close();
    }
}
