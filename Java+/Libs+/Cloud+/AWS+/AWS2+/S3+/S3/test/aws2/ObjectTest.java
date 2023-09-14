package aws2;

import aws1.Helper;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Upload, download and list objects in a bucket.
 */
class ObjectTest {
    private static final S3Client s3 = Helper.s3;

    @Test
    void putObject() {
        var bucket = Helper.createRandomBucket();

        var key = "dir1/file.txt";
        var request = PutObjectRequest.builder().bucket(bucket).key(key).build();
        var content = RequestBody.fromString("File content");
        var response = s3.putObject(request, content);
        assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();

        s3.deleteObject(b -> b.bucket(bucket).key(key));

        Helper.deleteBucket(bucket);
    }
}
