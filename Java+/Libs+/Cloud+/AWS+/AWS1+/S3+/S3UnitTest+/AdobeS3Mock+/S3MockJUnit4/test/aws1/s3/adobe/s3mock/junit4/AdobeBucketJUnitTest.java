package aws1.s3.adobe.s3mock.junit4;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListBucketsPaginatedRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class AdobeBucketJUnitTest extends AdobeBaseJUnitTest {

    @Test
    void listBuckets() {
        var bucketName = "abc";
        s3.createBucket(bucketName);
        var request = new ListBucketsPaginatedRequest().withPrefix(bucketName);
        var result = s3.listBuckets(request);
        var buckets = result.getBuckets().stream().map(Bucket::getName).collect(toList());
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
