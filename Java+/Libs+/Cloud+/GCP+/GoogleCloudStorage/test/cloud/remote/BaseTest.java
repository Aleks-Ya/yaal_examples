package cloud.remote;


import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public abstract class BaseTest {
    protected static final String bucketName = "iablokov-test-bucket";
    private static final String projectId = "enterprise-data-hub-dev";

    protected Storage getStorage() {
        try {
            return StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(getCredentialsIs()))
                    .setProjectId(projectId)
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
