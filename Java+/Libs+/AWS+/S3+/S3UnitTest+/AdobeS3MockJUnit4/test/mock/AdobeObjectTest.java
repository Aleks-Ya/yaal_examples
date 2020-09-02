package mock;

import com.adobe.testing.s3mock.junit4.S3MockRule;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

/**
 * Upload, download and list objects in a bucket.
 */
public class AdobeObjectTest {
    @ClassRule
    public static final S3MockRule S3_MOCK_RULE = S3MockRule.builder().silent().build();
    private final AmazonS3 s3 = S3_MOCK_RULE.createS3Client();

    @Test
    public void uploadListDeleteObject() {
        Bucket bucket = createBucket(s3);

        String contentExp = "abc";
        String keyName = uploadObject(s3, bucket, contentExp);

        listObjects(s3, bucket, keyName);

        String contentAct = downloadObject(s3, bucket, keyName);
        assertThat(contentAct, equalTo(contentExp));

        deleteObject(s3, bucket, keyName);
        deleteBucket(s3, bucket);
    }

    private static String downloadObject(AmazonS3 s3, Bucket bucket, String keyName) {
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

    private static void deleteBucket(AmazonS3 s3, Bucket bucket) {
        s3.deleteBucket(bucket.getName());
    }

    private static Bucket createBucket(AmazonS3 s3) {
        String bucketName = UUID.randomUUID().toString();
        return s3.createBucket(bucketName);
    }

    private static void deleteObject(AmazonS3 s3, Bucket bucket, String keyName) {
        s3.deleteObject(bucket.getName(), keyName);
    }

    private static void listObjects(AmazonS3 s3, Bucket bucket, String keyName) {
        ListObjectsV2Result result = s3.listObjectsV2(bucket.getName());
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        assertThat(objects, hasSize(1));
        S3ObjectSummary os = objects.get(0);
        assertThat(os.getKey(), equalTo(keyName));
    }

    private static String uploadObject(AmazonS3 s3, Bucket bucket, String content) {
        String keyName = "ObjectTest.txt";
        try {
            s3.putObject(bucket.getName(), keyName, content);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        return keyName;
    }
}
