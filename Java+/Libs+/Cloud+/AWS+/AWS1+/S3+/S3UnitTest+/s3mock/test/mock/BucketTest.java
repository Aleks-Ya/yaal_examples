package mock;

import com.amazonaws.services.s3.model.Bucket;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BucketTest extends S3MockBaseTest {

    @Test
    void listBuckets() {
        String bucketName = "abc";
        s3.createBucket(bucketName);
        List<String> buckets = s3.listBuckets().stream().map(Bucket::getName).collect(Collectors.toList());
        assertThat(buckets).containsExactly(bucketName);
    }

    @Test
    void createBucket() {
        String bucketName = UUID.randomUUID().toString();
        assertFalse(s3.doesBucketExistV2(bucketName));

        Bucket bucket = s3.createBucket(bucketName);
        assertThat(bucket.getName()).isEqualTo(bucketName);
        assertTrue(s3.doesBucketExistV2(bucketName));

        s3.deleteBucket(bucketName);
        assertFalse(s3.doesBucketExistV2(bucketName));

    }
}
