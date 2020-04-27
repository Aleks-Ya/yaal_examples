package mock;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import io.findify.s3mock.S3Mock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BucketTest {
    private static AmazonS3 s3client;
    private static S3Mock api;

    @BeforeClass
    public static void setUp() {
        api = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        api.start();
        String region = "us-west-2";
        String serviceEndpoint = "http://localhost:8001";
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, region);
        s3client = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
    }

    @Test
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

    @AfterClass
    public static void afterClass() {
        api.shutdown(); // kills the underlying actor system. Use api.stop() to just unbind the port.
    }
}
