package cloud.inmemory.object;

import cloud.inmemory.BaseInMemoryTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExistsObject extends BaseInMemoryTest {

    @Test
    public void notExists() {
        var blob = storage.get(BlobId.of(BUCKET_NAME, OBJECT_NAME));
        var exists = blob != null;
        assertFalse(exists);
    }

    @Test
    public void exists() {
        storage.create(BlobInfo.newBuilder(BUCKET_NAME, OBJECT_NAME).build(), "abc".getBytes());
        var blob = storage.get(BlobId.of(BUCKET_NAME, OBJECT_NAME));
        var exists = blob != null;
        assertTrue(exists);
    }

}
