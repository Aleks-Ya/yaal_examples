package aws2.s3;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.utils.StringInputStream;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static software.amazon.awssdk.core.sync.RequestBody.fromString;

/**
 * Upload, download and list objects in a bucket.
 */
class ObjectIT {
    private static final S3Client s3 = S3Helper.s3;

    @Test
    void listObjectsInEmptyBucket() {
        try (var bucket = S3Helper.randomBucket()) {
            var bucketName = bucket.name;
            var response = s3.listObjectsV2(b -> b.bucket(bucketName));
            var objects = response.contents();
            assertThat(objects).isEmpty();
        }
    }

    @Test
    void listObjects() {
        try (var bucket = S3Helper.randomBucket()) {
            var bucketName = bucket.name;
            var key1 = "dir1/file1.txt";
            var key2 = "dir1/file2.txt";
            assertThat(s3.putObject(o -> o.bucket(bucketName).key(key1), fromString("Content 1")).sdkHttpResponse().isSuccessful()).isTrue();
            assertThat(s3.putObject(o -> o.bucket(bucketName).key(key2), fromString("Content 2")).sdkHttpResponse().isSuccessful()).isTrue();

            var response = s3.listObjectsV2(b -> b.bucket(bucketName));
            var objects = response.contents();
            System.out.println("Objects: " + objects);
            var keys = objects.stream().map(S3Object::key).toList();
            assertThat(keys).containsExactlyInAnyOrder(key1, key2);
        }
    }

    @Test
    void putObjectFromString() {
        try (var bucket = S3Helper.randomBucket()) {
            var content = "File content";
            var key = "dir1/file.txt";
            var request = PutObjectRequest.builder().bucket(bucket.name).key(key).build();
            var body = fromString(content);
            var response = s3.putObject(request, body);
            assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();
        }
    }

    @Test
    void putObjectFromInputStream() {
        try (var bucket = S3Helper.randomBucket()) {
            var content = "File content";
            var contentLength = content.length();
            var is = new StringInputStream(content);

            var key = "dir1/file.txt";
            var request = PutObjectRequest.builder().bucket(bucket.name).key(key).build();
            var body = RequestBody.fromInputStream(is, contentLength);
            var response = s3.putObject(request, body);
            assertThat(response.sdkHttpResponse().isSuccessful()).isTrue();
        }
    }

    @Test
    void headObject_notExist() {
        try (var bucket = S3Helper.randomBucket()) {
            assertThatThrownBy(() -> {
                var request = HeadObjectRequest.builder().bucket(bucket.name).key("file.txt").build();
                s3.headObject(request);
            }).isInstanceOf(NoSuchKeyException.class);
        }
    }

    @Test
    void deleteObject() {
        try (var bucket = S3Helper.randomBucket()) {
            var bucketName = bucket.name;
            var key = "dir1/file.txt";
            assertThatThrownBy(() -> s3.headObject(o -> o.bucket(bucketName).key(key))).isInstanceOf(NoSuchKeyException.class);

            assertThat(s3.putObject(o -> o.bucket(bucketName).key(key), fromString("File content")).sdkHttpResponse().isSuccessful()).isTrue();
            assertThat(s3.headObject(o -> o.bucket(bucketName).key(key)).sdkHttpResponse().isSuccessful()).isTrue();

            s3.deleteObject(b -> b.bucket(bucketName).key(key));
            assertThatThrownBy(() -> s3.headObject(b -> b.bucket(bucketName).key(key))).isInstanceOf(NoSuchKeyException.class);
        }
    }

    @Test
    void deleteObjects() {
        try (var bucket = S3Helper.randomBucket()) {
            var bucketName = bucket.name;
            var key1 = "dir1/file1.txt";
            var key2 = "dir1/file2.txt";
            assertThatThrownBy(() -> s3.headObject(o -> o.bucket(bucketName).key(key1))).isInstanceOf(NoSuchKeyException.class);
            assertThatThrownBy(() -> s3.headObject(o -> o.bucket(bucketName).key(key2))).isInstanceOf(NoSuchKeyException.class);

            assertThat(s3.putObject(o -> o.bucket(bucketName).key(key1), fromString("Content 1")).sdkHttpResponse().isSuccessful()).isTrue();
            assertThat(s3.putObject(o -> o.bucket(bucketName).key(key2), fromString("Content 2")).sdkHttpResponse().isSuccessful()).isTrue();

            assertThat(s3.headObject(o -> o.bucket(bucketName).key(key1)).sdkHttpResponse().isSuccessful()).isTrue();
            assertThat(s3.headObject(o -> o.bucket(bucketName).key(key2)).sdkHttpResponse().isSuccessful()).isTrue();

            var objectIdentifiers = List.of(
                    ObjectIdentifier.builder().key(key1).build(),
                    ObjectIdentifier.builder().key(key2).build());
            s3.deleteObjects(b -> b.bucket(bucketName).delete(o -> o.objects(objectIdentifiers)));

            assertThatThrownBy(() -> s3.headObject(b -> b.bucket(bucketName).key(key1))).isInstanceOf(NoSuchKeyException.class);
            assertThatThrownBy(() -> s3.headObject(o -> o.bucket(bucketName).key(key2))).isInstanceOf(NoSuchKeyException.class);
        }
    }
}
