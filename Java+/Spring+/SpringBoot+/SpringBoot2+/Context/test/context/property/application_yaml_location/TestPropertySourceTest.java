package context.property.application_yaml_location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Use "application.yaml" location for unit-tests (with @TestPropertySource).
 * Root "application.yaml" is loaded.
 */
@SpringBootTest(webEnvironment = NONE)
@TestPropertySource(locations = "classpath:context/property/application_yaml_location/application.yaml")
class TestPropertySourceTest {

    @Value("${person}")
    private String person;

    @Value("${city}")
    private String city;

    @Autowired
    private Environment env;

    @Test
    void containsPropertyFromCustomApplicationYaml() {
        assertThat(env.containsProperty("person")).isTrue();
        assertThat(person).isEqualTo("Mary");
    }

    @Test
    void containsPropertyFromDefaultApplicationYaml() {
        assertThat(env.containsProperty("city")).isTrue();
        assertThat(city).isEqualTo("London");
    }
}