package cloud.remote.bucket;

import cloud.remote.BaseRemoteTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExistsBucketTest extends BaseRemoteTest {
    @Test
    void exists() {
        var bucket = storage.get(BUCKET_NAME);
        var exists = bucket != null;
        assertThat(exists).isTrue();
    }

    @Test
    void notExists() {
        var notExistsBucketName = "not-exists-bucket";
        var bucket = storage.get(notExistsBucketName);
        var exists = bucket != null;
        assertThat(exists).isFalse();
    }
}
