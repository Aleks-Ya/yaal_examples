package cloud.inmemory.object;

import cloud.inmemory.BaseInMemoryTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UploadObject extends BaseInMemoryTest {

    @Test
    public void uploadByCreate() {
        var blobId = BlobId.of(BUCKET_NAME, OBJECT_NAME);

        var blobInfo = BlobInfo.newBuilder(blobId).build();
        var blob = storage.create(blobInfo, CONTENT.getBytes());
        assertThat(blob, notNullValue());

        var actBlob = storage.get(blobId);
        var os = new ByteArrayOutputStream();
        actBlob.downloadTo(os);
        assertThat(os.toString(), equalTo(CONTENT));
    }

    @Test
    public void uploadByWriter() throws IOException {
        var blobId = BlobId.of(BUCKET_NAME, OBJECT_NAME);

        var blobInfo = BlobInfo.newBuilder(blobId).build();
        var blob = storage.create(blobInfo);
        try (var writer = blob.writer()) {
            writer.write(ByteBuffer.wrap(CONTENT.getBytes()));
        }

        var actBlob = storage.get(blobId);
        var os = new ByteArrayOutputStream();
        actBlob.downloadTo(os);
        assertThat(os.toString(), equalTo(CONTENT));
    }
}
