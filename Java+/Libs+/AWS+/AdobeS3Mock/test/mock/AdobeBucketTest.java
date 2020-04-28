package mock;

import com.adobe.testing.s3mock.junit4.S3MockRule;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdobeBucketTest {
    @ClassRule
    public static final S3MockRule S3_MOCK_RULE = S3MockRule.builder().silent().build();
    private final AmazonS3 s3client = S3_MOCK_RULE.createS3Client();

    @Test
    @Ignore("exception on listBuckets()")
    public void listBuckets() {
        String bucketName = "abc";
        s3client.createBucket(bucketName);
        List<String> buckets = s3client.listBuckets().stream().map(Bucket::getName).collect(Collectors.toList());
        assertThat(buckets, contains(bucketName));
    }

    @Test
    public void createBucket() {
        String bucketName = UUID.randomUUID().toString();
        assertFalse(s3client.doesBucketExistV2(bucketName));

        Bucket bucket = s3client.createBucket(bucketName);
        assertThat(bucket.getName(), equalTo(bucketName));
        assertTrue(s3client.doesBucketExistV2(bucketName));

        s3client.deleteBucket(bucketName);
        assertFalse(s3client.doesBucketExistV2(bucketName));
    }

}
