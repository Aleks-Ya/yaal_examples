package aws1.s3;

import com.amazonaws.AmazonServiceException;
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
class ObjectTest extends BaseS3Test {

    private static void uploadListDeleteObject(String keyName) {
        var bucket = createBucket();

        var contentExp = "the content";
        uploadObject(bucket, keyName, contentExp);

        listObjects(bucket, keyName);

        var contentAct = downloadObject(bucket, keyName);
        assertThat(contentAct).isEqualTo(contentExp);

        deleteObject(bucket, keyName);
        deleteBucket(bucket);
    }

    private static String downloadObject(Bucket bucket, String keyName) {
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

    private static void deleteBucket(Bucket bucket) {
        s3.deleteBucket(bucket.getName());
    }

    private static Bucket createBucket() {
        var bucketName = UUID.randomUUID().toString();
        return s3.createBucket(bucketName);
    }

    private static void deleteObject(Bucket bucket, String keyName) {
        s3.deleteObject(bucket.getName(), keyName);
    }

    private static void listObjects(Bucket bucket, String keyName) {
        var result = s3.listObjectsV2(bucket.getName());
        var objects = result.getObjectSummaries();
        assertThat(objects).hasSize(1);
        var os = objects.get(0);
        assertThat(os.getKey()).isEqualTo(keyName);
    }

    private static void uploadObject(Bucket bucket, String keyName, String content) {
        try {
            s3.putObject(bucket.getName(), keyName, content);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    @Test
    void uploadListDeleteObjectInRoot() {
        var keyName = "ObjectTest.txt";
        uploadListDeleteObject(keyName);
    }

    @Test
    void uploadListDeleteObjectInFolder() {
        var keyName = "abc/ObjectTest.txt";
        uploadListDeleteObject(keyName);
    }
}
