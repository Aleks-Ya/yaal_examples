package blob.local.azurite;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.models.BlobItem;
import org.junit.jupiter.api.Test;

import static blob.Factory.randomName;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use Azurite (a Local Blob Storage service) for testing.
 * Azurite Docker container is running with TestContainers.
 * Docs: https://microsoft.github.io/code-with-engineering-playbook/automated-testing/tech-specific-samples/blobstorage-unit-tests/
 */
class AzuriteTestContainersTest extends AzuriteBaseTest {
    @Test
    void uploadListDownloadDeleteBlob() {
        var blobServiceClient = initClient();
        var containerName = randomName();
        var blobContainerClient = blobServiceClient.createBlobContainer(containerName);

        assertThat(blobContainerClient.listBlobs()).isEmpty();

        var blobName = randomName();
        var blobClient = blobContainerClient.getBlobClient(blobName);
        var blobContent = "abc";
        blobClient.upload(BinaryData.fromString(blobContent));

        assertThat(blobContainerClient.listBlobs().stream().map(BlobItem::getName)).containsExactly(blobName);

        var binaryData = blobClient.downloadContent();
        assertThat(binaryData.toString()).isEqualTo(blobContent);

        blobClient.delete();
        assertThat(blobContainerClient.listBlobs().stream().map(BlobItem::getName)).isEmpty();
    }
}
