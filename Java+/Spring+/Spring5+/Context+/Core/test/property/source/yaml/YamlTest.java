package property.source.yaml;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Read properties from a YAML file.
 */
@Disabled("@PropertySource doesn't support parsing YAML natively.")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = YamlTest.ValueConfig.class)
class YamlTest {

    @Value("${planet.name}")
    private String planetName;

    @Test
    void yaml() {
        assertThat(planetName).isEqualTo("Jupiter");
    }

    @Configuration
    @PropertySource("classpath:property/source/value/value.yaml")
    static class ValueConfig {
    }
}