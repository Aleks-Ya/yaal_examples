package property.source.custom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use custom PropertySource.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomPropertyTest.Config.class)
class CustomPropertyTest {
    @Test
    void custom() {
        var propertyName = "person";
        var propertyValue = "John";
        var properties = Map.<String, Object>of(propertyName, propertyValue);
        var customPropertySource = new MapPropertySource("map-property-source", properties);

        var ctx = new GenericApplicationContext();
        var sources = ctx.getEnvironment().getPropertySources();
        sources.addFirst(customPropertySource);

        assertThat(ctx.getEnvironment().getProperty(propertyName)).isEqualTo(propertyValue);
    }

    @Configuration
    static class Config {
    }
}