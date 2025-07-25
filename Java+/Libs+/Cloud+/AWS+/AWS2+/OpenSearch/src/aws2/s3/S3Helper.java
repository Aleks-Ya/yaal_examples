package aws2.s3;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;

import java.util.UUID;

/**
 * Duplicate of "Java+/Libs+/Cloud+/AWS+/AWS2+/S3+/S3/src/aws2/s3/S3Helper.java"
 */
public class S3Helper {
    public static final S3Client s3 = S3Client.create();

    public static RandomBucket randomBucket() {
        return new RandomBucket();
    }

    private static String createRandomBucket() {
        var bucketName = "aws-unit-test-" + UUID.randomUUID();
        System.out.println("Creating bucket: " + bucketName);
        var createResponse = s3.createBucket(b -> b.bucket(bucketName));
        assert createResponse.sdkHttpResponse().isSuccessful();
        return bucketName;
    }

    private static void deleteBucket(String bucket) {
        System.out.println("Deleting bucket: " + bucket);
        var objectIds = s3.listObjectsV2(b1 -> b1.bucket(bucket)).contents().stream()
                .map(obj -> ObjectIdentifier.builder().key(obj.key()).build())
                .toList();
        var deleteObjectsRequest = DeleteObjectsRequest.builder().bucket(bucket).delete(b -> b.objects(objectIds)).build();
        assert s3.deleteObjects(deleteObjectsRequest).sdkHttpResponse().isSuccessful();
        assert s3.deleteBucket(b -> b.bucket(bucket)).sdkHttpResponse().isSuccessful();
    }

    public static class RandomBucket implements AutoCloseable {
        public final String name;

        private RandomBucket() {
            this.name = createRandomBucket();
        }

        @Override
        public void close() {
            deleteBucket(name);
        }
    }
}
