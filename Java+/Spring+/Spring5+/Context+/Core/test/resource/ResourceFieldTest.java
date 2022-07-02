package resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ResourceFieldTest.class)
class ResourceFieldTest {

    @Value("file:resourcesTest/resource/person.txt")
    private Resource fileResource;

    @Value("classpath:resource/city.txt")
    private Resource classpathResource;

    @Value("https://httpbin.org/base64/SFRUUEJJTiBpcyBhd2Vzb21l")
    private Resource urlResource;

    @Test
    void fileResource() throws IOException {
        var content = StreamUtils.copyToString(fileResource.getInputStream(), UTF_8);
        assertThat(content).isEqualTo("John");
    }

    @Test
    void classpathResource() throws IOException {
        var content = StreamUtils.copyToString(classpathResource.getInputStream(), UTF_8);
        assertThat(content).isEqualTo("London");
    }

    @Test
    void urlResource() throws IOException {
        var content = StreamUtils.copyToString(urlResource.getInputStream(), UTF_8);
        assertThat(content).isEqualTo("HTTPBIN is awesome");
    }
}