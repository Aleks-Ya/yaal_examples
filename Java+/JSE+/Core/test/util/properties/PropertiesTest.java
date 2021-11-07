package util.properties;

import org.junit.jupiter.api.Test;
import util.ResourceUtil;

import java.io.IOException;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Using {@link java.util.Properties}.
 */
class PropertiesTest {

    @Test
    void defaultProperties() {
        var key = "a";
        var value = "1";

        var defaultProps = new Properties();
        defaultProps.setProperty(key, value);

        var actual = new Properties(defaultProps);
        assertThat(actual.getProperty(key)).isEqualTo(value);

        assertThat(actual.size()).isEqualTo(0);
        assertThat(actual).isEmpty();
    }

    @Test
    void multilineValues() throws IOException {
        var actual = new Properties();
        actual.load(ResourceUtil.resourceToInputStream("util/properties/multiline.properties"));
        assertThat(actual).containsEntry("single.line.value", "Hello, Properties!");
        assertThat(actual).containsEntry("multi.line.value", "Hello, Properties!");
    }

}