package aws1.s3.adobemock;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Upload, download and list objects in a bucket.
 */
class AdobeObjectTest extends AdobeBaseTest {

    private static void deleteBucket(AmazonS3 s3, Bucket bucket) {
        s3.deleteBucket(bucket.getName());
    }

    private static void deleteObject(AmazonS3 s3, Bucket bucket, String keyName) {
        s3.deleteObject(bucket.getName(), keyName);
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

    @Test
    void uploadListDeleteObject() {
        Bucket bucket = createRandomBucket(s3);

        String contentExp = "abc";
        String keyName = uploadObject(s3, bucket, contentExp);

        assertObjects(bucket, keyName);

        String contentAct = downloadObject(bucket, keyName);
        assertThat(contentAct).isEqualTo(contentExp);

        deleteObject(s3, bucket, keyName);
        deleteBucket(s3, bucket);
    }

    private void assertObjects(Bucket bucket, String keyName) {
        List<S3ObjectSummary> objects = listObjects(bucket);
        assertThat(objects).hasSize(1);
        S3ObjectSummary os = objects.get(0);
        assertThat(os.getKey()).isEqualTo(keyName);
    }
}
