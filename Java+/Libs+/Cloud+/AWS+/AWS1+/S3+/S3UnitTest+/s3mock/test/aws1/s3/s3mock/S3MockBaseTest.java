package aws1.s3.s3mock;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public abstract class S3MockBaseTest {
    protected static AmazonS3 s3;
    private static S3Mock api;

    @BeforeAll
    public static void setUp() {
        api = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        api.start();
        String region = "us-west-2";
        String serviceEndpoint = "http://localhost:8001";
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, region);
        s3 = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();
    }

    @AfterAll
    public static void afterClass() {
        api.shutdown(); // kills the underlying actor system. Use api.stop() to just unbind the port.
    }

    protected Bucket createRandomBucket(AmazonS3 s3) {
        String bucketName = UUID.randomUUID().toString();
        return s3.createBucket(bucketName);
    }

    protected String downloadObject(Bucket bucket, String keyName) {
        try {
            S3Object o = s3.getObject(bucket.getName(), keyName);
            S3ObjectInputStream s3is = o.getObjectContent();
            OutputStream os = new ByteArrayOutputStream();
            byte[] read_buf = new byte[1024];
            int read_len;
            while ((read_len = s3is.read(read_buf)) > 0) {
                os.write(read_buf, 0, read_len);
            }
            s3is.close();
            os.close();
            return os.toString();
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            throw e;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected List<S3ObjectSummary> listObjects(Bucket bucket) {
        ListObjectsV2Result result = s3.listObjectsV2(bucket.getName());
        return result.getObjectSummaries();
    }
}
