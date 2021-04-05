package cloud.remote;

import com.google.cloud.storage.BlobId;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DownloadObject extends BaseRemoteTest {
    @Test
    public void download() {
        var objectName = "file1.txt";
        var os = new ByteArrayOutputStream();
        var blob = storage.get(BlobId.of(BUCKET_NAME, objectName));
        blob.downloadTo(os);
        assertThat(os.toString(), equalTo("abc"));
    }
}
