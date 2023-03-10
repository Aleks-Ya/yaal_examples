package property.source.factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PropertySourceFactoryTest.Config.class)
class PropertySourceFactoryTest {

    @Value("${my.ocean.name}")
    private String oceanName;

    @Test
    void value() {
        assertThat(oceanName).isEqualTo("Pacific");
    }

    @Configuration
    @PropertySource(value = "file:${user.dir}/resourcesTest/property/source/factory/factory.properties",
            factory = PrefixPropertySourceFactory.class)
    static class Config {
    }
}