package blob.local.azurite;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AzuriteBaseTest {
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

    protected BlobServiceClient initClient() {
        var credential = new StorageSharedKeyCredential(ACCOUNT_NAME, ACCOUNT_KEY);
        var endpoint = String.format("http://%s:%s/%s", azurite.getHost(), PORT, ACCOUNT_NAME);
        return new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
    }
}
