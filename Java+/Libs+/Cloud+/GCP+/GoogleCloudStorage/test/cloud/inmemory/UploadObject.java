package cloud.inmemory;


import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.contrib.nio.testing.LocalStorageHelper;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class UploadObject {
    @Test
    public void upload() throws IOException {
        var bucketName = "the_bucket";
        var objectName = "file.txt";
        var content = "the content";
        var blobId = BlobId.of(bucketName, objectName);

        var storage = LocalStorageHelper.getOptions().getService();

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
