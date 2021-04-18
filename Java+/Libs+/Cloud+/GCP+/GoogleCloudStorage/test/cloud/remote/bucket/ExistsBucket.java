package cloud.remote.bucket;

import cloud.remote.BaseRemoteTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExistsBucket extends BaseRemoteTest {
    @Test
    public void exists() {
        var bucket = storage.get(BUCKET_NAME);
        var exists = bucket != null;
        assertTrue(exists);
    }

    @Test
    public void notExists() {
        var notExistsBucketName = "not-exists-bucket";
        var bucket = storage.get(notExistsBucketName);
        var exists = bucket != null;
        assertFalse(exists);
    }
}
