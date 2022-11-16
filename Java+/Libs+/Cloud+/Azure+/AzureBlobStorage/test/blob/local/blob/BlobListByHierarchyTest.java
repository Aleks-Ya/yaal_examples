package blob.local.blob;

import blob.local.azurite.AzuriteBaseTest;
import com.azure.storage.blob.models.BlobItem;
import org.junit.jupiter.api.Test;

import static blob.Factory.createBlob;
import static blob.Factory.randomName;
import static org.assertj.core.api.Assertions.assertThat;

class BlobListByHierarchyTest extends AzuriteBaseTest {
    @Test
    void listBlobsInDirExcludingSubDirs() {
        var blobServiceClient = initClient();
        var containerName = randomName();
        var blobContainerClient = blobServiceClient.createBlobContainer(containerName);

        assertThat(blobContainerClient.listBlobs()).isEmpty();

        var rootDirA = "rootDirA";
        var rootDirB = "rootDirB";
        var subDirA1 = "subDirA1";
        var subDirA2 = "subDirA2";

        var blobName1 = createBlob(blobContainerClient, rootDirA, "file1.txt");
        var blobName2 = createBlob(blobContainerClient, rootDirA, "file2.txt");
        var blobName3 = createBlob(blobContainerClient, rootDirB, "file3.txt");
        var blobName4 = createBlob(blobContainerClient, rootDirB, "file4.txt");
        var blobName5 = createBlob(blobContainerClient, rootDirA, subDirA1, "file5.txt");
        var blobName6 = createBlob(blobContainerClient, rootDirA, subDirA2, "file6.txt");

        assertThat(blobContainerClient.listBlobs().stream().map(BlobItem::getName))
                .containsExactlyInAnyOrder(blobName1, blobName2, blobName3, blobName4, blobName5, blobName6);

        var rootBlobItems = blobContainerClient.listBlobsByHierarchy(rootDirA);
        assertThat(rootBlobItems.stream().map(BlobItem::getName)).containsExactly("rootDirA/");
        var rootBlobA = rootBlobItems.stream().findFirst().orElseThrow();
        var itemsBlobA = blobContainerClient.listBlobsByHierarchy(rootBlobA.getName());
        assertThat(itemsBlobA.stream().map(BlobItem::getName)).containsExactlyInAnyOrder(
                "rootDirA/subDirA1/", "rootDirA/subDirA2/", "rootDirA/file1.txt", "rootDirA/file2.txt");
    }
}
