package aws1.s3;

import software.amazon.awssdk.services.s3.S3Client;

import java.util.UUID;

public class Helper {
    public static final S3Client s3 = S3Client.create();

    public static String createRandomBucket() {
        var bucketName = "aws-unit-test-" + UUID.randomUUID();
        var createResponse = s3.createBucket(b -> b.bucket(bucketName));
        assert createResponse.sdkHttpResponse().isSuccessful();
        return bucketName;
    }

    public static void deleteBucket(String bucket) {
        var response = s3.deleteBucket(b -> b.bucket(bucket));
        assert response.sdkHttpResponse().isSuccessful();
    }
}
