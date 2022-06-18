package azure.azurite;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobServiceClient;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import util.RandomUtil;

import java.io.IOException;

import static azure.azurite.BlobServiceTest.ACCOUNT_KEY;
import static azure.azurite.BlobServiceTest.ACCOUNT_NAME;
import static azure.azurite.BlobServiceTest.PORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@SpringBootConfiguration
@EnableAutoConfiguration
@ContextConfiguration(classes = {BlobServiceImpl.class, BlobServiceConfig.class})
@TestPropertySource(properties = {
        "spring.cloud.azure.storage.blob.account-name=" + ACCOUNT_NAME,
        "spring.cloud.azure.storage.blob.account-key=" + ACCOUNT_KEY,
        "spring.cloud.azure.storage.blob.endpoint=http://localhost:" + PORT + "/" + ACCOUNT_NAME
})
@Testcontainers
class BlobServiceTest {

    static final String ACCOUNT_NAME = "devstoreaccount1";
    static final String ACCOUNT_KEY = "Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==";
    static final int PORT = 10000;
    @Container
    @SuppressWarnings("resource")
    private final GenericContainer<?> azurite =
            new GenericContainer<>("mcr.microsoft.com/azure-storage/azurite")
                    .withExposedPorts(PORT)
                    .withCommand("docker-entrypoint.sh azurite-blob --blobHost 0.0.0.0")
                    .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(new HostConfig()
                            .withPortBindings(new PortBinding(Ports.Binding.bindPort(PORT), new ExposedPort(PORT)))))
                    .waitingFor(Wait.forHttp("/" + ACCOUNT_NAME + "?comp=list").forPort(PORT).forStatusCode(403));
    @Autowired
    private BlobService blobService;

    @Autowired
    private BlobServiceClient blobServiceClient;

    @Test
    void readBlobContent() throws IOException {
        var expContent = "data1";
        var containerName = "container-" + RandomUtil.randomIntPositive();
        var fileName = "data.txt";
        var blobContainerClient = blobServiceClient.createBlobContainer(containerName);
        var blobClient = blobContainerClient.getBlobClient(fileName);
        blobClient.upload(BinaryData.fromString(expContent));

        var content = blobService.getBlobContent(containerName, fileName);
        assertThat(content).isEqualTo(expContent);
    }
}