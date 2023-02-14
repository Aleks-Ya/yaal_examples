package context.property.configuration_properties.on_record.enable_configuration_properties;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Use @ConfigurationProperties annotation on a record (using @EnableConfigurationProperties).
 */
@SpringBootTest(webEnvironment = NONE,
        properties = "spring.config.location=classpath:context/property/configuration_properties/application.yaml")
@TestPropertySource(properties = "PREVIOUS_URI=http://m.city.ru:7777/info")
@SpringBootConfiguration
@EnableConfigurationProperties(CityRecord.class)
class RecordEnableConfigurationPropertiesTest {

    @Autowired
    private CityRecord cityOnRecord;

    @Test
    void cityAnnotationOnRecord() {
        assertThat(cityOnRecord.toString()).isEqualTo(
                "CityRecord[title=Moscow, year=1147, uri=http://moscow.russia.ru:8080/info, previousUri=http://m.city.ru:7777/info]");
        assertThat(cityOnRecord.uri()).isEqualTo(URI.create("http://moscow.russia.ru:8080/info"));
    }

}