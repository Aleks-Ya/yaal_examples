package aws1.s3.adobe.s3mock.junit5.extension;

import com.adobe.testing.s3mock.junit5.S3MockExtension;
import com.adobe.testing.s3mock.util.DigestUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import util.FileUtil;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

class S3MockExtensionProgrammaticTest {

    @RegisterExtension
    static final S3MockExtension S3_MOCK = S3MockExtension.builder().silent()
            .withSecureConnection(false).build();
    private static final String BUCKET_NAME = "mydemotestbucket";
    private final AmazonS3 s3Client = S3_MOCK.createS3Client();

    @Test
    void shouldUploadAndDownloadObject() throws Exception {
        var uploadFile = FileUtil.createAbsentTempFile();
        Files.writeString(uploadFile.toPath(), "demo=content");

        s3Client.createBucket(BUCKET_NAME);
        s3Client.putObject(new PutObjectRequest(BUCKET_NAME, uploadFile.getName(), uploadFile));

        var s3Object = s3Client.getObject(BUCKET_NAME, uploadFile.getName());

        var uploadFileIs = Files.newInputStream(uploadFile.toPath());
        var uploadDigest = DigestUtil.hexDigest(uploadFileIs);
        var downloadedDigest = DigestUtil.hexDigest(s3Object.getObjectContent());
        uploadFileIs.close();
        s3Object.close();

        assertThat(uploadDigest)
                .as("Up- and downloaded Files should have equal digests")
                .isEqualTo(downloadedDigest);
    }
}