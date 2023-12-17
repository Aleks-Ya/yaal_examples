package aws1.s3;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Create, list, delete S3 buckets.
 * Requires aws1.key.access and aws1.key.secret Java properties.
 */
class BucketTest extends BaseS3Test {

    @Test
    void listBuckets() {
        var buckets = s3.listBuckets();
        System.out.println("Buckets: " + buckets);
        assertThat(buckets).isNotEmpty();
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
