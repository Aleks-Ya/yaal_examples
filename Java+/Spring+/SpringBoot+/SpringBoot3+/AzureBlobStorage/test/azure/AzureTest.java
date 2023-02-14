package azure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import util.InputStreamUtil;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
@SpringBootConfiguration
@EnableAutoConfiguration
@PropertySource("file:/home/aleks/.azure-examples/spring-azure.properties")
class AzureTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void readBlobContent() throws IOException {
        var fileName = "data.txt";
        var containerName = "container1";
        var storageBlobResource = resourceLoader.getResource("azure-blob://" + containerName + "/" + fileName);
        var content = InputStreamUtil.inputStreamToString(storageBlobResource.getInputStream());
        assertThat(content).isEqualTo("abc");
    }
}