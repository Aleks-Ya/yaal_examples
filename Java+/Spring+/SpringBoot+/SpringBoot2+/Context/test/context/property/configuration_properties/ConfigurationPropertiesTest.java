package context.property.configuration_properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Use @{@link org.springframework.boot.context.properties.ConfigurationProperties} annotation.
 */
@SpringBootTest(webEnvironment = NONE, classes = {Config.class, CityOnClass.class},
        properties = "spring.config.location=classpath:context/property/configuration_properties/application.yaml")
@TestPropertySource(properties = "PREVIOUS_URI=http://m.city.ru:7777/info")
class ConfigurationPropertiesTest {

    @Autowired
    private Person person;

    @Autowired
    private CityOnMethod cityOnMethod;

    @Autowired
    private CityOnClass cityOnClass;

    @Autowired
    private AllInfo allInfo;

    @Test
    void person() {
        assertThat(person.name()).isEqualTo("John");
        assertThat(person.age()).isEqualTo(30);
    }

    @Test
    void cityAnnotationOnBeanMethod() {
        assertThat(cityOnMethod.toString())
                .isEqualTo("Moscow-1147 at http://moscow.russia.ru:8080/info or at http://m.city.ru:7777/info");
        assertThat(cityOnMethod.getUri()).isEqualTo(URI.create("http://moscow.russia.ru:8080/info"));
    }

    @Test
    void cityAnnotationOnClass() {
        assertThat(cityOnClass.toString())
                .isEqualTo("Moscow-1147 at http://moscow.russia.ru:8080/info or at http://m.city.ru:7777/info");
        assertThat(cityOnClass.getUri()).isEqualTo(URI.create("http://moscow.russia.ru:8080/info"));
    }

    @Test
    void allInfo() {
        assertThat(allInfo.toString())
                .isEqualTo("John-30 from Moscow-1147 at http://moscow.russia.ru:8080/info or at http://m.city.ru:7777/info");
    }

}