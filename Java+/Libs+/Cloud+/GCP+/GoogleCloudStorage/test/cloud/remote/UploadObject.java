package cloud.remote;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UploadObject extends BaseRemoteTest {
    @Test
    public void uploadByCreate() {
        var objectName = "file2.txt";
        var content = "the content " + new Random().nextInt(Integer.MAX_VALUE);
        var blobId = BlobId.of(bucketName, objectName);

        var blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.delete(blobId);
        var blob = storage.create(blobInfo, content.getBytes());
        assertThat(blob, notNullValue());

        var actBlob = storage.get(blobId);
        var os = new ByteArrayOutputStream();
        actBlob.downloadTo(os);
        assertThat(os.toString(), equalTo(content));
    }
}
