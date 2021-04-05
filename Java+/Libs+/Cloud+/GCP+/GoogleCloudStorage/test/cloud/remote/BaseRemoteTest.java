package cloud.remote;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class BaseRemoteTest {
    protected static final String BUCKET_NAME = "iablokov-test-bucket";
    private static final String PROJECT_ID = "enterprise-data-hub-dev";
    protected final Storage storage = getStorage();

    private Storage getStorage() {
        try {
            return StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(getCredentialsIs()))
                    .setProjectId(PROJECT_ID)
                    .build()
                    .getService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getCredentialsIs() {
        try {
            var credentialsFile = System.getProperty("credentialsFile");
            if (credentialsFile == null) {
                throw new RuntimeException("'credentialsFile' system property absents");
            }
            return new FileInputStream(credentialsFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
