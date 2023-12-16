package gcp.storage.inmemory.object;

import gcp.storage.inmemory.BaseInMemoryTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

class UploadObjectTest extends BaseInMemoryTest {

    @Test
    void uploadByCreate() {
        var blobId = BlobId.of(BUCKET_NAME, OBJECT_NAME);

        var blobInfo = BlobInfo.newBuilder(blobId).build();
        var blob = storage.create(blobInfo, CONTENT.getBytes());
        assertThat(blob).isNotNull();

        var actBlob = storage.get(blobId);
        var os = new ByteArrayOutputStream();
        actBlob.downloadTo(os);
        assertThat(os.toString()).isEqualTo(CONTENT);
    }

    @Test
    void uploadByWriter() throws IOException {
        var blobId = BlobId.of(BUCKET_NAME, OBJECT_NAME);

        var blobInfo = BlobInfo.newBuilder(blobId).build();
        var blob = storage.create(blobInfo);
        try (var writer = blob.writer()) {
            writer.write(ByteBuffer.wrap(CONTENT.getBytes()));
        }

        var actBlob = storage.get(blobId);
        var os = new ByteArrayOutputStream();
        actBlob.downloadTo(os);
        assertThat(os.toString()).isEqualTo(CONTENT);
    }
}
