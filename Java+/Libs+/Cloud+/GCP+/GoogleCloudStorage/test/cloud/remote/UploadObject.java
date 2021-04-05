package cloud.remote;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UploadObject extends BaseTest {
    @Test
    public void upload() throws IOException {
        var objectName = "file2.txt";
        var content = "the content " + new Random().nextInt(Integer.MAX_VALUE);
        var blobId = BlobId.of(bucketName, objectName);

        var storage = getStorage();

        var blobInfo = BlobInfo.newBuilder(blobId).build();
        var blob = storage.create(blobInfo, content.getBytes());
        try (var writer = blob.writer()) {
            writer.write(ByteBuffer.wrap(content.getBytes()));
        }

        var actBlob = storage.get(blobId);
        var os = new ByteArrayOutputStream();
        actBlob.downloadTo(os);
        assertThat(os.toString(), equalTo(content));
    }
}
