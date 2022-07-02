package resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ResourceLoaderTest.class)
class ResourceLoaderTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void fileResource() throws IOException {
        var resource = resourceLoader.getResource("file:resourcesTest/resource/person.txt");
        var content = StreamUtils.copyToString(resource.getInputStream(), UTF_8);
        assertThat(content).isEqualTo("John");
    }

    @Test
    void classpathResource() throws IOException {
        var resource = resourceLoader.getResource("classpath:resource/city.txt");
        var content = StreamUtils.copyToString(resource.getInputStream(), UTF_8);
        assertThat(content).isEqualTo("London");
    }

    @Test
    void urlResource() throws IOException {
        var resource = resourceLoader.getResource("https://httpbin.org/base64/SFRUUEJJTiBpcyBhd2Vzb21l");
        var content = StreamUtils.copyToString(resource.getInputStream(), UTF_8);
        assertThat(content).isEqualTo("HTTPBIN is awesome");
    }
}