package spring.boot.test.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "person.name=John", classes = PropertiesInAnnotationTest.Config.class)
@SpringBootConfiguration
class PropertiesInAnnotationTest {
    @Value("${person.name}")
    private String name;

    @Test
    void property() {
        assertThat(name).isEqualTo("John");
    }

    @Configuration
    static class Config {
    }
}