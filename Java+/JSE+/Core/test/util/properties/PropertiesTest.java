package util.properties;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        assertThat(actual.getProperty(key), equalTo(value));

        assertThat(actual.size(), equalTo(0));
        assertThat(actual.entrySet(), Matchers.empty());
    }

}