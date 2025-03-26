package aws1.s3.adobe.s3mock.junit5;

import com.adobe.testing.s3mock.junit5.S3MockExtension;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@ExtendWith(S3MockExtension.class)
public abstract class AdobeBaseTest {
    protected static AmazonS3 s3;

    @BeforeAll
    public static void setUp(AmazonS3 s3) {
        AdobeBaseTest.s3 = s3;
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
