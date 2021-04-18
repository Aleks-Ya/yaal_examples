package aws;

import com.amazonaws.services.s3.model.Bucket;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Create, list, delete S3 buckets.
 * Requires aws.key.access and aws.key.secret Java properties.
 */
public class BucketTest extends BaseS3Test {

    @Test
    public void listBuckets() {
        List<Bucket> buckets = s3.listBuckets();
        assertThat(buckets, not(emptyIterable()));
    }

    @Test
    public void createBucket() {
        String bucketName = UUID.randomUUID().toString();
        assertFalse(s3.doesBucketExistV2(bucketName));

        Bucket bucket = s3.createBucket(bucketName);
        assertThat(bucket.getName(), equalTo(bucketName));
        assertTrue(s3.doesBucketExistV2(bucketName));

        s3.deleteBucket(bucketName);
        assertFalse(s3.doesBucketExistV2(bucketName));

    }
}
