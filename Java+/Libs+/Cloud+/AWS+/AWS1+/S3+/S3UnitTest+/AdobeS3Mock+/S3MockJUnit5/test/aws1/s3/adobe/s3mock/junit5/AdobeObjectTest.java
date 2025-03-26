package aws1.s3.adobe.s3mock.junit5;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.junit.jupiter.api.Test;

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
        var bucket = createRandomBucket(s3);

        var contentExp = "abc";
        var keyName = uploadObject(s3, bucket, contentExp);

        assertObjects(bucket, keyName);

        var contentAct = downloadObject(bucket, keyName);
        assertThat(contentAct).isEqualTo(contentExp);

        deleteObject(s3, bucket, keyName);
        deleteBucket(s3, bucket);
    }

    private void assertObjects(Bucket bucket, String keyName) {
        var objects = listObjects(bucket);
        assertThat(objects).hasSize(1);
        var os = objects.getFirst();
        assertThat(os.getKey()).isEqualTo(keyName);
    }
}
