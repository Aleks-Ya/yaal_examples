package cloud.remote.object;

import cloud.remote.BaseRemoteTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.jupiter.api.Test;
import util.RandomUtil;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class UploadObjectTest extends BaseRemoteTest {
    @Test
    void uploadByCreate() {
        var objectName = "dir1/file2.txt";
        var content = "the content " + RandomUtil.randomIntPositive();
        var blobId = BlobId.of(BUCKET_NAME, objectName);

        var blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.delete(blobId);
        var blob = storage.create(blobInfo, content.getBytes());
        assertThat(blob).isNotNull();

        var actBlob = storage.get(blobId);
        var os = new ByteArrayOutputStream();
        actBlob.downloadTo(os);
        assertThat(os).hasToString(content);
    }
}
