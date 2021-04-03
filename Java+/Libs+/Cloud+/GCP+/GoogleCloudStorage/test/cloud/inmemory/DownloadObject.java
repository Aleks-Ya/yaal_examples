package cloud.inmemory;


import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.contrib.nio.testing.LocalStorageHelper;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class DownloadObject {
    @Test
    public void download() {
        var objectName = "file.txt";
        var content = "the content";
        var storage = LocalStorageHelper.getOptions().getService();
        var bucketName = "the_bucket";
        storage.create(BlobInfo.newBuilder(bucketName, objectName).build(), content.getBytes());
        var os = new ByteArrayOutputStream();
        var blob = storage.get(BlobId.of(bucketName, objectName));
        blob.downloadTo(os);
        assertThat(os.toString(), equalTo(content));
    }

}
