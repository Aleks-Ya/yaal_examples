package aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.jupiter.api.Test;

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
 * Requires aws.key.access and aws.key.secret Java properties.
 */
public class ObjectTest extends BaseS3Test {

    @Test
    public void uploadListDeleteObjectInRoot() {
        String keyName = "ObjectTest.txt";
        uploadListDeleteObject(keyName);
    }

    @Test
    public void uploadListDeleteObjectInFolder() {
        String keyName = "abc/ObjectTest.txt";
        uploadListDeleteObject(keyName);
    }

    private static void uploadListDeleteObject(String keyName) {
        Bucket bucket = createBucket();

        String contentExp = "the content";
        uploadObject(bucket, keyName, contentExp);

        listObjects(bucket, keyName);

        String contentAct = downloadObject(bucket, keyName);
        assertThat(contentAct, equalTo(contentExp));

        deleteObject(bucket, keyName);
        deleteBucket(bucket);
    }

    private static String downloadObject(Bucket bucket, String keyName) {
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

    private static void deleteBucket(Bucket bucket) {
        s3.deleteBucket(bucket.getName());
    }

    private static Bucket createBucket() {
        String bucketName = UUID.randomUUID().toString();
        return s3.createBucket(bucketName);
    }

    private static void deleteObject(Bucket bucket, String keyName) {
        s3.deleteObject(bucket.getName(), keyName);
    }

    private static void listObjects(Bucket bucket, String keyName) {
        ListObjectsV2Result result = s3.listObjectsV2(bucket.getName());
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        assertThat(objects, hasSize(1));
        S3ObjectSummary os = objects.get(0);
        assertThat(os.getKey(), equalTo(keyName));
    }

    private static void uploadObject(Bucket bucket, String keyName, String content) {
        try {
            s3.putObject(bucket.getName(), keyName, content);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }
}
