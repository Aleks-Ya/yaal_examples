package blob.real;

import blob.Factory;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.models.BlobItem;
import org.junit.jupiter.api.Test;

import static blob.Factory.randomName;
import static org.assertj.core.api.Assertions.assertThat;

class BlobTest {
    @Test
    void uploadListDownloadDeleteBlob() {
        var blobServiceClient = Factory.realBlobServiceClient();
        var containerName = randomName();
        blobServiceClient.createBlobContainer(containerName);

        var blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
        var blobs = blobContainerClient.listBlobs().stream().map(BlobItem::getName).toList();
        assertThat(blobs).isEmpty();

        var blobName = randomName();
        var blobClient = blobContainerClient.getBlobClient(blobName);
        var blobContent = "abc";
        blobClient.upload(BinaryData.fromString(blobContent));

        var blobs2 = blobContainerClient.listBlobs().stream().map(BlobItem::getName).toList();
        assertThat(blobs2).contains(blobName);

        var binaryData = blobClient.downloadContent();
        assertThat(binaryData.toString()).isEqualTo(blobContent);

        blobClient.delete();
        var blobs3 = blobContainerClient.listBlobs().stream().map(BlobItem::getName).toList();
        assertThat(blobs3).isEmpty();
    }

}
