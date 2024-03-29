package gcp.storage.remote.object;

import gcp.storage.remote.BaseRemoteTest;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;

class AppendObjectTest extends BaseRemoteTest {
    @Test
    void appendObjectByCompose() {
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

        assertThat(storage.delete(appendBlobId)).isTrue();

        var actTargetBlob = storage.get(targetBlobId);
        var os = new ByteArrayOutputStream();
        actTargetBlob.downloadTo(os);
        assertThat(os.toString()).isEqualTo(targetContent + appendContent);
    }
}
