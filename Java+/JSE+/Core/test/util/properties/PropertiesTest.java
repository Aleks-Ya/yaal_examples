package util.properties;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Using {@link java.util.Properties}.
 */
public class PropertiesTest {

    @Test
    public void defaultProperties() {
        String key = "a";
        String value = "1";

        Properties defaultProps = new Properties();
        defaultProps.setProperty(key, value);

        Properties actual = new Properties(defaultProps);
        assertThat(actual.getProperty(key), equalTo(value));

        assertThat(actual.size(), equalTo(0));
        assertThat(actual.entrySet(), Matchers.empty());
    }

}