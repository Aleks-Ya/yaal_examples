package cloud.remote;


import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.StorageOptions;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class DownloadObject extends BaseTest {
    @Test
    public void download() throws IOException {
        var objectName = "file1.txt";
        var storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(getCredentialsIs()))
                .setProjectId(projectId)
                .build()
                .getService();
        var os = new ByteArrayOutputStream();
        var blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(os);
        assertThat(os.toString(), equalTo("abc"));
    }

}
