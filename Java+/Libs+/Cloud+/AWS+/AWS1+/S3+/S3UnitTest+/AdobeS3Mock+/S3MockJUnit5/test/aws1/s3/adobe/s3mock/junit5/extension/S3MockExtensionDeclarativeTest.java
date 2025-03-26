package aws1.s3.adobe.s3mock.junit5.extension;

import com.adobe.testing.s3mock.junit5.S3MockExtension;
import com.adobe.testing.s3mock.util.DigestUtil;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import util.FileUtil;

import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(S3MockExtension.class)
class S3MockExtensionDeclarativeTest {
    private static final String BUCKET_NAME = "mydemotestbucket";

    @Test
    void shouldUploadAndDownloadObject(final AmazonS3 s3Client) throws Exception {
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
