package mock;

import com.adobe.testing.s3mock.S3MockApplication;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

public abstract class AdobeBaseTest {
    protected static S3MockApplication server;
    protected static AmazonS3 s3;

    @BeforeAll
    public static void setUp() {
        server = S3MockApplication.start();
        var serviceEndpoint = "http://localhost:" + server.getHttpPort() + "/";
        var endpointConfiguration =
                new AwsClientBuilder.EndpointConfiguration(serviceEndpoint, Regions.EU_CENTRAL_1.getName());
        s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .enablePathStyleAccess()
                .build();
    }

    @AfterAll
    public static void tearDown() {
        s3.shutdown();
        server.stop();
    }

    protected Bucket createRandomBucket(AmazonS3 s3) {
        var bucketName = UUID.randomUUID().toString();
        return s3.createBucket(bucketName);
    }

    protected String downloadObject(Bucket bucket, String keyName) {
        try {
            var o = s3.getObject(bucket.getName(), keyName);
            var s3is = o.getObjectContent();
            OutputStream os = new ByteArrayOutputStream();
            var read_buf = new byte[1024];
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
        var result = s3.listObjectsV2(bucket.getName());
        return result.getObjectSummaries();
    }

}
