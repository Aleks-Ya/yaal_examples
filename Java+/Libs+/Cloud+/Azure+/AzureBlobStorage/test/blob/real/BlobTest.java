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
        var blobContainerClient = blobServiceClient.createBlobContainer(containerName);

        assertThat(blobContainerClient.listBlobs()).isEmpty();

        var blobName = randomName();
        var blobClient = blobContainerClient.getBlobClient(blobName);
        var blobContent = "abc";
        blobClient.upload(BinaryData.fromString(blobContent));

        assertThat(blobContainerClient.listBlobs().stream().map(BlobItem::getName)).contains(blobName);

        var binaryData = blobClient.downloadContent();
        assertThat(binaryData.toString()).isEqualTo(blobContent);

        blobClient.delete();
        assertThat(blobContainerClient.listBlobs()).isEmpty();
    }

}
