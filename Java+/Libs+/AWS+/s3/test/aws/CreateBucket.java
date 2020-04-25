package aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CreateBucket {
    private static AmazonS3 s3client;

    @BeforeClass
    public static void setUp() {
        String accessKey = System.getProperty("aws.key.access");
        String secretKey = System.getProperty("aws.key.secret");
        assertThat(accessKey, allOf(not(emptyString()), notNullValue()));
        assertThat(secretKey, allOf(not(emptyString()), notNullValue()));

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }

    @Test
    public void listBuckets() {
        List<Bucket> buckets = s3client.listBuckets();
        assertThat(buckets, not(emptyIterable()));
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
