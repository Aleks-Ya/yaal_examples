package cloud.inmemory;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DownloadObject extends BaseInMemoryTest {
    @Test
    public void download() {
        storage.create(BlobInfo.newBuilder(BUCKET_NAME, OBJECT_NAME).build(), CONTENT.getBytes());
        var os = new ByteArrayOutputStream();
        var blob = storage.get(BlobId.of(BUCKET_NAME, OBJECT_NAME));
        blob.downloadTo(os);
        assertThat(os.toString(), equalTo(CONTENT));
    }
}
