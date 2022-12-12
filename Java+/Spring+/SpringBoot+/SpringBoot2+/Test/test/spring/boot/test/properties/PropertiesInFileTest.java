package spring.boot.test.properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "person.age=35", classes = PropertiesInFileTest.Config.class)
@TestPropertySource(locations = "classpath:spring/boot/test/properties/PropertiesInFileTest.properties")
class PropertiesInFileTest {
    @Value("${person.name}")
    private String name;
    @Value("${person.age}")
    private Integer age;

    @Test
    void property() {
        assertThat(name).isEqualTo("John");
        assertThat(age).isEqualTo(35);
    }

    @Configuration
    static class Config {
    }
}