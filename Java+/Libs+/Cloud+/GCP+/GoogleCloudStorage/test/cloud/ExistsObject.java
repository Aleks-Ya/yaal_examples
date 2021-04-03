package cloud;


import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.StorageOptions;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class ExistsObject {
    @Test
    public void notExists() throws IOException {
        var projectId = "enterprise-data-hub-dev";
        var bucketName = "iablokov-test-bucket";
        var objectName = "not_exists.txt";
        var credentialsFile = System.getProperty("credentialsFile");
        if (credentialsFile == null) {
            throw new RuntimeException("'credentialsFile' system property absents");
        }
        var storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(credentialsFile)))
                .setProjectId(projectId)
                .build()
                .getService();
        var blob = storage.get(BlobId.of(bucketName, objectName));
        var exists = blob != null;
        assertThat(exists, equalTo(false));
    }

    @Test
    public void exists() throws IOException {
        var projectId = "enterprise-data-hub-dev";
        var bucketName = "iablokov-test-bucket";
        var objectName = "exists.txt";
        var credentialsFile = System.getProperty("credentialsFile");
        if (credentialsFile == null) {
            throw new RuntimeException("'credentialsFile' system property absents");
        }
        var storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(credentialsFile)))
                .setProjectId(projectId)
                .build()
                .getService();
        storage.create(BlobInfo.newBuilder(bucketName, objectName).build(), "abc".getBytes());
        var blob = storage.get(BlobId.of(bucketName, objectName));
        var exists = blob != null;
        assertThat(exists, equalTo(true));
    }

}
