package cloud.remote.object;

import cloud.remote.BaseRemoteTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExistsObject extends BaseRemoteTest {
    @Test
    public void notExists() {
        var objectName = "not_exists.txt";
        var blob = storage.get(BlobId.of(BUCKET_NAME, objectName));
        var exists = blob != null;
        assertFalse(exists);
    }

    @Test
    public void exists() {
        var objectName = "exists.txt";
        storage.create(BlobInfo.newBuilder(BUCKET_NAME, objectName).build(), "abc".getBytes());
        var blob = storage.get(BlobId.of(BUCKET_NAME, objectName));
        var exists = blob != null;
        assertTrue(exists);
    }
}
