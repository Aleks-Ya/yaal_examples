package gcp.storage.remote.object;

import gcp.storage.remote.BaseRemoteTest;
import com.google.cloud.storage.BlobId;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class DownloadObjectTest extends BaseRemoteTest {
    @Test
    void download() {
        var objectName = "file1.txt";
        var os = new ByteArrayOutputStream();
        var blob = storage.get(BlobId.of(BUCKET_NAME, objectName));
        blob.downloadTo(os);
        assertThat(os.toString()).isEqualTo("abc");
    }
}
