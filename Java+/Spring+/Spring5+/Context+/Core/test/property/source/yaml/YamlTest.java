package property.source.yaml;

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
 * {@code @PropertySource} does not support parsing YAML natively.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = YamlTest.ValueConfig.class)
class YamlTest {
    @Value("${planet.name}")
    private String planetName;
    @Value("${planet}")
    private String planet;
    @Value("${name}")
    private String name;

    @Test
    void yaml() {
        assertThat(planetName).isEqualTo("${planet.name}");
        assertThat(planet).isEmpty();
        assertThat(name).isEqualTo("Jupiter");
    }

    @Configuration
    @PropertySource("classpath:property/source/yaml/value.yaml")
    static class ValueConfig {
    }
}