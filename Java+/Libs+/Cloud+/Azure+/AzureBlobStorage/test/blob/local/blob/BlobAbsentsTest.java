package blob.local.blob;

import blob.local.azurite.AzuriteBaseTest;
import com.azure.storage.blob.models.BlobStorageException;
import org.junit.jupiter.api.Test;

import static blob.Factory.randomName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlobAbsentsTest extends AzuriteBaseTest {
    @Test
    void isBlobExists() {
        var blobServiceClient = initClient();
        var containerName = randomName();
        var blobContainerClient = blobServiceClient.createBlobContainer(containerName);
        var blobClient = blobContainerClient.getBlobClient("absent.txt");
        assertThat(blobClient.exists()).isFalse();
    }

    @Test
    void downloadAbsentBlob() {
        var blobServiceClient = initClient();
        var containerName = randomName();
        var blobContainerClient = blobServiceClient.createBlobContainer(containerName);
        var blobClient = blobContainerClient.getBlobClient("absent.txt");
        assertThatThrownBy(blobClient::downloadContent)
                .isInstanceOf(BlobStorageException.class)
                .hasMessageContaining("<Code>BlobNotFound</Code>");
    }
}
