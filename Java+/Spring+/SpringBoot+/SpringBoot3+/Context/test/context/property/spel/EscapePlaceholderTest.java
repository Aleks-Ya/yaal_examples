package context.property.spel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Escape a placeholder in "application.yaml" in order to substitute it at runtime.
 * (No need to escape)
 */
@SpringBootTest(webEnvironment = NONE,
        properties = "spring.config.location=classpath:context/property/spel/escape.yaml")
@SpringBootConfiguration
class EscapePlaceholderTest {

    @Value("${url}")
    private String urlTemplate;

    @Test
    void test() {
        assertThat(urlTemplate).isEqualTo("https://server1:1000/dev/${location}");
        var url = urlTemplate.replace("${location}", "data/actual");
        assertThat(url).isEqualTo("https://server1:1000/dev/data/actual");
    }
}