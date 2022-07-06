package resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StreamUtils;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ResourceConstructorTest.class)
class ResourceConstructorTest {

    @Test
    void fileResource() throws IOException {
        var resource = new FileSystemResource("resourcesTest/resource/person.txt");
        var content = StreamUtils.copyToString(resource.getInputStream(), UTF_8);
        assertThat(content).isEqualTo("John");
    }

    @Test
    void classpathResource_ThreadClassLoader() throws IOException {
        var resource = new ClassPathResource("resource/city.txt");
        var content = StreamUtils.copyToString(resource.getInputStream(), UTF_8);
        assertThat(content).isEqualTo("London");
    }

    @Test
    void classpathResource_Class() throws IOException {
        var resource = new ClassPathResource("city.txt", ResourceConstructorTest.class);
        var content = StreamUtils.copyToString(resource.getInputStream(), UTF_8);
        assertThat(content).isEqualTo("London");
    }

    @Test
    void urlResource() throws IOException {
        var resource = new UrlResource("https://httpbin.org/base64/SFRUUEJJTiBpcyBhd2Vzb21l");
        var content = StreamUtils.copyToString(resource.getInputStream(), UTF_8);
        assertThat(content).isEqualTo("HTTPBIN is awesome");
    }
}