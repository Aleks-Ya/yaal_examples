package property.source.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Specify absolute property file path instead of using class path.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PropertyFileTest.PropertyFileConfig.class)
class PropertyFileTest {

    @Value("${planet.name}")
    private String planetName;

    @Test
    void value() {
        assertThat(planetName).isEqualTo("Jupiter");
    }

    @Configuration
    @PropertySource("file:${user.dir}/resourcesTest/property/source/file/file.properties")
    static class PropertyFileConfig {
    }
}