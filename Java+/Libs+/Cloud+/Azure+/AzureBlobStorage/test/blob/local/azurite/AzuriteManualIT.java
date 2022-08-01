package blob.local.azurite;

import blob.Factory;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.models.BlobItem;
import org.junit.jupiter.api.Test;

import static blob.Factory.randomName;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use Azurite (a Local Blob Storage service) for testing.
 * Azurite Docker container is running manually.
 * Docs: https://microsoft.github.io/code-with-engineering-playbook/automated-testing/tech-specific-samples/blobstorage-unit-tests/
 */
class AzuriteManualIT {
    @Test
    void uploadListDownloadDeleteBlob() {
        var blobServiceClient = Factory.azuriteBlobServiceClient();
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
