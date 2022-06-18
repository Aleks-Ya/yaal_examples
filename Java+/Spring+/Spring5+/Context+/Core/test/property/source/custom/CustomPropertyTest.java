package property.source.custom;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Use custom PropertySource.
 */
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
}