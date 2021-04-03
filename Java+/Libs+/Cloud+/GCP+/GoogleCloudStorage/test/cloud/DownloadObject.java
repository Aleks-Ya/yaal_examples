package cloud;


import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.StorageOptions;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class DownloadObject {
    @Test
    public void download() throws IOException {
        var projectId = "enterprise-data-hub-dev";
        var bucketName = "iablokov-test-bucket";
        var objectName = "file1.txt";
        var credentialsFile = System.getProperty("credentialsFile");
        if (credentialsFile == null) {
            throw new RuntimeException("'credentialsFile' system property absents");
        }
        var storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(credentialsFile)))
                .setProjectId(projectId)
                .build()
                .getService();
        var os = new ByteArrayOutputStream();
        var blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(os);
        assertThat(os.toString(), equalTo("abc"));
    }

}
