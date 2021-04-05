package cloud.remote;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class AppendObject extends BaseRemoteTest {
    @Test
    public void appendObjectByCompose() {
        var targetObjectName = "append/target.txt";
        var targetBlobId = BlobId.of(BUCKET_NAME, targetObjectName);
        var targetBlobInfo = BlobInfo.newBuilder(targetBlobId).build();
        var targetContent = "the content 1";
        storage.delete(targetBlobId);
        var targetBlob = storage.create(targetBlobInfo, targetContent.getBytes());

        var appendObjectName = "append/append.txt";
        var appendBlobId = BlobId.of(BUCKET_NAME, appendObjectName);
        var appendBlobInfo = BlobInfo.newBuilder(appendBlobId).build();
        var appendContent = "the content 2";
        storage.delete(appendBlobId);
        var appendBlob = storage.create(appendBlobInfo, appendContent.getBytes());

        var composeRequest = Storage.ComposeRequest.newBuilder()
                .addSource(targetBlob.getName(), appendBlob.getName())
                .setTarget(targetBlob)
                .build();
        storage.compose(composeRequest);

        assertTrue(storage.delete(appendBlobId));

        var actTargetBlob = storage.get(targetBlobId);
        var os = new ByteArrayOutputStream();
        actTargetBlob.downloadTo(os);
        assertThat(os.toString(), equalTo(targetContent + appendContent));
    }
}
