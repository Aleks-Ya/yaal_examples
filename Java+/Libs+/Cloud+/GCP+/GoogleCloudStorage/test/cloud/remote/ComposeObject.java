package cloud.remote;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ComposeObject extends BaseRemoteTest {
    @Test
    public void compose() {
        var objectName1 = "compose/file1.txt";
        var blobId1 = BlobId.of(bucketName, objectName1);
        var blobInfo1 = BlobInfo.newBuilder(blobId1).build();
        var content1 = "the content 1";
        storage.delete(blobId1);
        var blob1 = storage.create(blobInfo1, content1.getBytes());

        var objectName2 = "compose/file2.txt";
        var blobId2 = BlobId.of(bucketName, objectName2);
        var blobInfo2 = BlobInfo.newBuilder(blobId2).build();
        var content2 = "the content 2";
        storage.delete(blobId2);
        var blob2 = storage.create(blobInfo2, content2.getBytes());

        var targetObjectName = "compose/target.txt";
        var targetBlobId = BlobId.of(bucketName, targetObjectName);
        var targetBlob = BlobInfo.newBuilder(targetBlobId).build();

        var composeRequest = Storage.ComposeRequest.newBuilder()
                .addSource(blob1.getName(), blob2.getName())
                .setTarget(targetBlob)
                .build();
        storage.compose(composeRequest);

        var actTargetBlob = storage.get(targetBlobId);
        var os = new ByteArrayOutputStream();
        actTargetBlob.downloadTo(os);
        assertThat(os.toString(), equalTo(content1 + content2));
    }
}
