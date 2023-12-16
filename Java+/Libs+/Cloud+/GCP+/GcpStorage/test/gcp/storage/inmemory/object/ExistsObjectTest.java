package gcp.storage.inmemory.object;

import gcp.storage.inmemory.BaseInMemoryTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExistsObjectTest extends BaseInMemoryTest {

    @Test
    void notExists() {
        var blob = storage.get(BlobId.of(BUCKET_NAME, OBJECT_NAME));
        var exists = blob != null;
        assertThat(exists).isFalse();
    }

    @Test
    void exists() {
        storage.create(BlobInfo.newBuilder(BUCKET_NAME, OBJECT_NAME).build(), "abc".getBytes());
        var blob = storage.get(BlobId.of(BUCKET_NAME, OBJECT_NAME));
        var exists = blob != null;
        assertThat(exists).isTrue();
    }

}
