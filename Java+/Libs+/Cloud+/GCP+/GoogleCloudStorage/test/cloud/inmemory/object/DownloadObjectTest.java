package cloud.inmemory.object;

import cloud.inmemory.BaseInMemoryTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class DownloadObjectTest extends BaseInMemoryTest {
    @Test
    public void download() {
        storage.create(BlobInfo.newBuilder(BUCKET_NAME, OBJECT_NAME).build(), CONTENT.getBytes());
        var os = new ByteArrayOutputStream();
        var blob = storage.get(BlobId.of(BUCKET_NAME, OBJECT_NAME));
        blob.downloadTo(os);
        assertThat(os.toString()).isEqualTo(CONTENT);
    }
}
