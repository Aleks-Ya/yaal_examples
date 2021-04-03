package cloud.inmemory;


import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.contrib.nio.testing.LocalStorageHelper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ExistsObject {

    @Test
    public void notExists() {
        var objectName = "not_exists.txt";
        var bucketName = "the_bucket";
        var storage = LocalStorageHelper.getOptions().getService();
        var blob = storage.get(BlobId.of(bucketName, objectName));
        var exists = blob != null;
        assertFalse(exists);
    }

    @Test
    public void exists() {
        var objectName = "exists.txt";
        var bucketName = "the_bucket";
        var storage = LocalStorageHelper.getOptions().getService();
        storage.create(BlobInfo.newBuilder(bucketName, objectName).build(), "abc".getBytes());
        var blob = storage.get(BlobId.of(bucketName, objectName));
        var exists = blob != null;
        assertTrue(exists);
    }

}
