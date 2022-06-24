package mock;

import com.amazonaws.services.s3.model.Bucket;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AdobeBucketTest extends AdobeBaseTest {

    @Test
    void listBuckets() {
        var bucketName = "abc";
        s3.createBucket(bucketName);
        var buckets = s3.listBuckets().stream().map(Bucket::getName).collect(Collectors.toList());
        assertThat(buckets).containsExactly(bucketName);
    }

    @Test
    void createBucket() {
        var bucketName = UUID.randomUUID().toString();
        assertThat(s3.doesBucketExistV2(bucketName)).isFalse();

        var bucket = s3.createBucket(bucketName);
        assertThat(bucket.getName()).isEqualTo(bucketName);
        assertThat(s3.doesBucketExistV2(bucketName)).isTrue();

        s3.deleteBucket(bucketName);
        assertThat(s3.doesBucketExistV2(bucketName)).isFalse();
    }


}
