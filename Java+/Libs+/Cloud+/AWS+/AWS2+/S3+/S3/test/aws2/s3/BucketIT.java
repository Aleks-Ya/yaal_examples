package aws2.s3;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Create, list, delete S3 buckets.
 * Requires aws.key.access and aws.key.secret Java properties.
 */
class BucketIT {
    private static final S3Client s3 = S3Helper.s3;

    @Test
    void listBuckets() {
        var response = s3.listBuckets();
        var buckets = response.buckets();
        System.out.println("Buckets: " + buckets);
        assertThat(buckets).isNotEmpty();
    }

    @Test
    void headBucket_notExist() {
        assertThatThrownBy(() -> {
            var request = HeadBucketRequest.builder().bucket("bucket-does-not-exist").build();
            s3.headBucket(request);
        }).isInstanceOf(NoSuchBucketException.class);
    }

    @Test
    void createDeleteBucket() {
        var bucketName = UUID.randomUUID().toString();
        assertThatThrownBy(() -> s3.headBucket(b -> b.bucket(bucketName))).isInstanceOf(NoSuchBucketException.class);

        var createRequest = CreateBucketRequest.builder().bucket(bucketName).build();
        var createResponse = s3.createBucket(createRequest);
        assertThat(createResponse.sdkHttpResponse().isSuccessful()).isTrue();
        var existResponse = s3.headBucket(b -> b.bucket(bucketName));
        assertThat(existResponse.sdkHttpResponse().isSuccessful()).isTrue();

        var deleteRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
        var deleteResponse = s3.deleteBucket(deleteRequest);
        assertThat(deleteResponse.sdkHttpResponse().isSuccessful()).isTrue();
        assertThatThrownBy(() -> s3.headBucket(b -> b.bucket(bucketName))).isInstanceOf(NoSuchBucketException.class);
    }
}
