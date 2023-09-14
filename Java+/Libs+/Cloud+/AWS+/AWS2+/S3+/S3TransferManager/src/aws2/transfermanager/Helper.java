package aws2.transfermanager;

import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

import java.util.UUID;

public class Helper {
    public static final S3Client s3 = S3Client.create();
    public static final S3AsyncClient s3Async = S3AsyncClient.create();
    public static final S3TransferManager tm = S3TransferManager.builder().s3Client(s3Async).build();

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
