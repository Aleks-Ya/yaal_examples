package cloud;


import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.StorageOptions;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class UploadObject {
    @Test
    public void upload() throws IOException {
        var projectId = "enterprise-data-hub-dev";
        var bucketName = "iablokov-test-bucket";
        var objectName = "file2.txt";
        var content = "the content " + new Random().nextInt(Integer.MAX_VALUE);
        var credentialsFile = System.getProperty("credentialsFile");
        if (credentialsFile == null) {
            throw new RuntimeException("'credentialsFile' system property absents");
        }
        var storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(credentialsFile)))
                .setProjectId(projectId)
                .build()
                .getService();
        var blob = storage.create(BlobInfo.newBuilder(bucketName, objectName).build(), content.getBytes());

        var os = new ByteArrayOutputStream();
        blob.downloadTo(os);
        assertThat(os.toString(), equalTo(content));
    }

}
