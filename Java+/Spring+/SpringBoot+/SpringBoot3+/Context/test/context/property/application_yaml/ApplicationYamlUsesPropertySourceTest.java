package context.property.application_yaml;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * application.yaml uses properties defined in a @PropertySource.
 */
@SpringBootTest(webEnvironment = NONE, classes = ApplicationYamlUsesPropertySourceTest.class,
        properties = "spring.config.location=classpath:context/property/application_yaml/ApplicationYamlUsesPropertySourceTest.yaml")
@Configuration
@PropertySource("classpath:context/property/application_yaml/ApplicationYamlUsesPropertySourceTest.properties")
class ApplicationYamlUsesPropertySourceTest {

    @Value("${person.text}")
    private String text;

    @Value("${person.name}")
    private String person;

    @Value("${person.age}")
    private Integer age;

    @Test
    void versus() {
        assertThat(person).isEqualTo("Mark");
        assertThat(age).isEqualTo(20);
        assertThat(text).isEqualTo("Mark-20");
    }
}