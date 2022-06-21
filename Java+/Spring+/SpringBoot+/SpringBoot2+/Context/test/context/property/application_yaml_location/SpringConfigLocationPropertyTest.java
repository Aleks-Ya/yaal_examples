package context.property.application_yaml_location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Use "application.yaml" location for unit-tests (with "spring.config.location" property).
 * Root "application.yaml" isn't loaded.
 */
@SpringBootTest(webEnvironment = NONE,
        properties = "spring.config.location=classpath:context/property/application_yaml_location/application.yaml")
@SpringBootConfiguration
class SpringConfigLocationPropertyTest {

    @Value("${person}")
    private String person;

    @Autowired
    private Environment env;

    @Test
    void containsPropertyFromCustomApplicationYaml() {
        assertThat(env.containsProperty("person")).isTrue();
        assertThat(person).isEqualTo("Mary");
    }

    @Test
    void notContainPropertyFromDefaultApplicationYaml() {
        assertThat(env.containsProperty("city")).isFalse();
    }
}