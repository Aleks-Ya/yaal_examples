package context.property.application_yaml;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * application.yaml properties wins @PropertySource properties.
 */
@SpringBootTest(webEnvironment = NONE, classes = ApplicationYamlVsPropertySourceTest.class,
        properties = "spring.config.location=classpath:context/property/application_yaml/ApplicationYamlVsPropertySourceTest.yaml")
@Configuration
@PropertySource("classpath:context/property/application_yaml/ApplicationYamlVsPropertySourceTest.properties")
class ApplicationYamlVsPropertySourceTest {

    @Value("${person.name}")
    private String person;

    @Value("${person.age}")
    private Integer age;

    @Test
    void versus() {
        assertThat(person).isEqualTo("John");
        assertThat(age).isEqualTo(25);
    }

}