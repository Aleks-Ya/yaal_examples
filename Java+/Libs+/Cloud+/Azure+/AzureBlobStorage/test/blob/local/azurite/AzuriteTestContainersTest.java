package blob.local.azurite;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static blob.Factory.randomName;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use Azurite (a Local Blob Storage service) for testing.
 * Azurite Docker container is running with TestContainers.
 * Docs: https://microsoft.github.io/code-with-engineering-playbook/automated-testing/tech-specific-samples/blobstorage-unit-tests/
 */
@Testcontainers
class AzuriteTestContainersTest {
    private static final String ACCOUNT_NAME = "devstoreaccount1";
    private static final String ACCOUNT_KEY = "Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==";
    private static final int PORT = 10000;
    @Container
    @SuppressWarnings("resource")
    private final GenericContainer<?> azurite =
            new GenericContainer<>("mcr.microsoft.com/azure-storage/azurite")
                    .withExposedPorts(PORT)
                    .withCommand("docker-entrypoint.sh azurite-blob --blobHost 0.0.0.0")
                    .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(new HostConfig()
                            .withPortBindings(new PortBinding(Ports.Binding.bindPort(PORT), new ExposedPort(PORT)))))
                    .waitingFor(Wait.forHttp("/" + ACCOUNT_NAME + "?comp=list").forPort(PORT).forStatusCode(403));

    private BlobServiceClient initClient() {
        var credential = new StorageSharedKeyCredential(ACCOUNT_NAME, ACCOUNT_KEY);
        var endpoint = String.format("http://%s:%s/%s", azurite.getHost(), PORT, ACCOUNT_NAME);
        return new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
    }

    @Test
    void uploadListDownloadDeleteBlob() {
        var blobServiceClient = initClient();
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
