package aws2.transfermanager;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;

/**
 * Upload, download and list objects in a bucket.
 */
class ObjectTest {
    private static final S3Client s3 = Helper.s3;
    private static final S3TransferManager tm = Helper.tm;

    @Test
    void uploadObject() {
        var bucket = Helper.createRandomBucket();

        var key = "dir1/file.txt";
        var putRequest = PutObjectRequest.builder().bucket(bucket).key(key).build();

        var body = AsyncRequestBody.fromString("File content");
        var request = UploadRequest.builder().putObjectRequest(putRequest).requestBody(body).build();
        var response = tm.upload(request);
        response.completionFuture().join();

        s3.deleteObject(b -> b.bucket(bucket).key(key));

        Helper.deleteBucket(bucket);
    }
}
