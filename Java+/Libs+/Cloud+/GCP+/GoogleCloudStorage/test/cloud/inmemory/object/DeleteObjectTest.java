package cloud.inmemory.object;

import cloud.inmemory.BaseInMemoryTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteObjectTest extends BaseInMemoryTest {
    @Test
    void deleteExisting() {
        var blobId = BlobId.of(BUCKET_NAME, OBJECT_NAME);
        var blobInfo = BlobInfo.newBuilder(blobId).build();
        var blob = storage.create(blobInfo, CONTENT.getBytes());
        assertThat(blob).isNotNull();

        assertThat(storage.get(blobId)).isNotNull();

        var result = storage.delete(blobId);
        assertThat(result).isTrue();

        assertThat(storage.get(blobId)).isNull();
    }

    @Test
    void deleteNotExisting() {
        var blobId = BlobId.of(BUCKET_NAME, OBJECT_NAME);
        assertThat(storage.get(blobId)).isNull();

        var result = storage.delete(blobId);
        assertThat(result).isFalse();

        assertThat(storage.get(blobId)).isNull();
    }
}
