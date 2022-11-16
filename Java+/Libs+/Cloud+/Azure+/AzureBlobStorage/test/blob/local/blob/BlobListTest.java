package blob.local.blob;

import blob.local.azurite.AzuriteBaseTest;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.ListBlobsOptions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.regex.Pattern;

import static blob.Factory.createBlob;
import static blob.Factory.randomName;
import static org.assertj.core.api.Assertions.assertThat;

class BlobListTest extends AzuriteBaseTest {
    @Test
    void listBlobsInDir() {
        var blobServiceClient = initClient();
        var containerName = randomName();
        var blobContainerClient = blobServiceClient.createBlobContainer(containerName);

        assertThat(blobContainerClient.listBlobs()).isEmpty();

        var dir1 = "dir1";
        var dir2 = "dir2";

        var blobName1 = createBlob(blobContainerClient, dir1, "file1.txt");
        var blobName2 = createBlob(blobContainerClient, dir1, "file2.txt");
        var blobName3 = createBlob(blobContainerClient, dir2, "file3.txt");
        var blobName4 = createBlob(blobContainerClient, dir2, "file4.txt");

        assertThat(blobContainerClient.listBlobs().stream().map(BlobItem::getName))
                .containsExactlyInAnyOrder(blobName1, blobName2, blobName3, blobName4);

        var listBlobsOptions = new ListBlobsOptions();
        listBlobsOptions.setPrefix(dir1);
        var dir1Blobs = blobContainerClient.listBlobs(listBlobsOptions, Duration.ofSeconds(5))
                .stream().map(BlobItem::getName);
        assertThat(dir1Blobs).containsExactlyInAnyOrder("dir1/file1.txt", "dir1/file2.txt");
    }

    @Test
    void filterBlobsByRegex() {
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

        var rootBlobItems = blobContainerClient.listBlobs();
        assertThat(rootBlobItems.stream().map(BlobItem::getName)).containsExactlyInAnyOrder(
                "rootDirA/file1.txt",
                "rootDirA/file2.txt",
                "rootDirA/subDirA1/file5.txt",
                "rootDirA/subDirA2/file6.txt",
                "rootDirB/file3.txt",
                "rootDirB/file4.txt");
        var pattern = Pattern.compile("rootDirA/.*\\.txt");
        assertThat(rootBlobItems.stream()
                .filter(blobItem -> pattern.matcher(blobItem.getName()).matches())
                .map(BlobItem::getName)).containsExactlyInAnyOrder(
                "rootDirA/file1.txt",
                "rootDirA/file2.txt",
                "rootDirA/subDirA1/file5.txt",
                "rootDirA/subDirA2/file6.txt");
    }
}
