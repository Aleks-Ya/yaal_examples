package aws1.s3.adobe.s3mock.junit4;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Upload, download and list objects in a bucket.
 */
class AdobeObjectJUnitTest extends AdobeBaseJUnitTest {

    private static String downloadObject(AmazonS3 s3, Bucket bucket, String keyName) {
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

    private static void deleteBucket(AmazonS3 s3, Bucket bucket) {
        s3.deleteBucket(bucket.getName());
    }

    private static Bucket createBucket(AmazonS3 s3) {
        var bucketName = UUID.randomUUID().toString();
        return s3.createBucket(bucketName);
    }

    private static void deleteObject(AmazonS3 s3, Bucket bucket, String keyName) {
        s3.deleteObject(bucket.getName(), keyName);
    }

    private static void listObjects(AmazonS3 s3, Bucket bucket, String keyName) {
        var result = s3.listObjectsV2(bucket.getName());
        var objects = result.getObjectSummaries();
        assertThat(objects).hasSize(1);
        var os = objects.getFirst();
        assertThat(os.getKey()).isEqualTo(keyName);
    }

    private static String uploadObject(AmazonS3 s3, Bucket bucket, String content) {
        var keyName = "ObjectTest.txt";
        try {
            s3.putObject(bucket.getName(), keyName, content);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
        return keyName;
    }

    @Test
    void uploadListDeleteObject() {
        var bucket = createBucket(s3);

        var contentExp = "abc";
        var keyName = uploadObject(s3, bucket, contentExp);

        listObjects(s3, bucket, keyName);

        var contentAct = downloadObject(s3, bucket, keyName);
        assertThat(contentAct).isEqualTo(contentExp);

        deleteObject(s3, bucket, keyName);
        deleteBucket(s3, bucket);
    }
}
