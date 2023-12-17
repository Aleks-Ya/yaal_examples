package aws1.s3.adobemock.junit4;

import com.amazonaws.services.s3.model.Bucket;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AdobeBucketJUnitTest extends AdobeBaseJUnitTest {

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
        assertThat(s3.doesBucketExistV2(bucketName)).isFalse();

        Bucket bucket = s3.createBucket(bucketName);
        assertThat(bucket.getName()).isEqualTo(bucketName);
        assertThat(s3.doesBucketExistV2(bucketName)).isTrue();

        s3.deleteBucket(bucketName);
        assertThat(s3.doesBucketExistV2(bucketName)).isFalse();
    }

}
