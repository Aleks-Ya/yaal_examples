package cloud.remote;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExistsObject extends BaseTest {
    @Test
    public void notExists() {
        var objectName = "not_exists.txt";
        var storage = getStorage();
        var blob = storage.get(BlobId.of(bucketName, objectName));
        var exists = blob != null;
        assertFalse(exists);
    }

    @Test
    public void exists() {
        var objectName = "exists.txt";
        var storage = getStorage();
        storage.create(BlobInfo.newBuilder(bucketName, objectName).build(), "abc".getBytes());
        var blob = storage.get(BlobId.of(bucketName, objectName));
        var exists = blob != null;
        assertTrue(exists);
    }
}
