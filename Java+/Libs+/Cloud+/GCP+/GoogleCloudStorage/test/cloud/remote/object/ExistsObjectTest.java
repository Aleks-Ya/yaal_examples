package cloud.remote.object;

import cloud.remote.BaseRemoteTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExistsObjectTest extends BaseRemoteTest {
    @Test
    void notExists() {
        var objectName = "not_exists.txt";
        var blob = storage.get(BlobId.of(BUCKET_NAME, objectName));
        var exists = blob != null;
        assertThat(exists).isFalse();
    }

    @Test
    void exists() {
        var objectName = "exists.txt";
        storage.create(BlobInfo.newBuilder(BUCKET_NAME, objectName).build(), "abc".getBytes());
        var blob = storage.get(BlobId.of(BUCKET_NAME, objectName));
        var exists = blob != null;
        assertThat(exists).isTrue();
    }
}
