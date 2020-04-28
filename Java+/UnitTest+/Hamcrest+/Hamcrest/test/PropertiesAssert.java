import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.not;

/**
 * Test {@link Properties} (= {@link java.util.Map}).
 */
public class PropertiesAssert {

    @Test
    public void emptyProperties() {
        assertThat(new Properties(), anEmptyMap());
    }

    @Test
    public void properties() {
        Properties props = new Properties();
        String key = "key";
        String value = "value";
        props.put(key, value);

        assertThat(props, not(anEmptyMap()));
        assertThat(props, aMapWithSize(1));
        assertThat(props, hasKey(key));
        assertThat(props, hasValue(value));
        assertThat(props, hasEntry(key, value));
        assertThat(props, hasEntry(equalTo(key), equalTo(value)));
        assertThat(props, allOf(
                hasEntry(equalTo(key), equalTo(value)),
                hasEntry(equalTo(key), equalTo(value))
        ));
    }
}