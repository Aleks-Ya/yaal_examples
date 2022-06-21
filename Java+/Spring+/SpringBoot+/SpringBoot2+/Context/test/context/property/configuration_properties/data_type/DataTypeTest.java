package context.property.configuration_properties.data_type;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static context.property.configuration_properties.data_type.AnimalType.CAT_ANIMAL;
import static context.property.configuration_properties.data_type.AnimalType.DOG_ANIMAL;
import static context.property.configuration_properties.data_type.Gender.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Parse different data types with @ConfigurationProperties.
 */
@SpringBootTest(webEnvironment = NONE,
        properties = "spring.config.location=classpath:context/property/configuration_properties/data_type/data_type.yaml")
@SpringBootConfiguration
@EnableConfigurationProperties(Person.class)
class DataTypeTest {

    @Autowired
    private Person person;

    @Test
    void person() {
        assertThat(person.name()).isEqualTo("John");
        assertThat(person.age()).isEqualTo(30);
        assertThat(person.gender()).isEqualTo(MALE);
        assertThat(person.webSite()).isEqualTo(URI.create("https://john.com/me"));
        assertThat(person.animals()).isEqualTo(Map.of(
                DOG_ANIMAL, List.of("Charlie", "Buddy"),
                CAT_ANIMAL, List.of("Milo", "Simba")
        ));
    }

}