package cloud.inmemory;


import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.contrib.nio.testing.LocalStorageHelper;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class UploadObject {
    @Test
    public void upload() {
        var objectName = "file.txt";
        var content = "the content";
        var bucketName = "the_bucket";
        var storage = LocalStorageHelper.getOptions().getService();
        var blob = storage.create(BlobInfo.newBuilder(bucketName, objectName).build(), content.getBytes());
        var os = new ByteArrayOutputStream();
        blob.downloadTo(os);
        assertThat(os.toString(), equalTo(content));
    }
}
