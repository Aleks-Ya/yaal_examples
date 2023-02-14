package context.property.configuration_properties.on_record.configuration_roperties_can;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Use @ConfigurationProperties annotation on a record (using @ConfigurationPropertiesScan).
 */
@SpringBootTest(webEnvironment = NONE,
        properties = "spring.config.location=classpath:context/property/configuration_properties/application.yaml")
@TestPropertySource(properties = "PREVIOUS_URI=http://m.city.ru:7777/info")
@SpringBootConfiguration
@ConfigurationPropertiesScan
class RecordConfigurationPropertiesScanTest {

    @Autowired
    private CityRecord cityOnRecord;

    @Test
    void cityAnnotationOnRecord() {
        assertThat(cityOnRecord.toString()).isEqualTo(
                "CityRecord[title=Moscow, year=1147, uri=http://moscow.russia.ru:8080/info, previousUri=http://m.city.ru:7777/info]");
        assertThat(cityOnRecord.uri()).isEqualTo(URI.create("http://moscow.russia.ru:8080/info"));
    }

}