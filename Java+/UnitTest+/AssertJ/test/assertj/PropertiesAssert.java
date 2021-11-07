package assertj;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class PropertiesAssert {
    @Test
    void properties() {
        var properties = new Properties();
        var key1 = "the.key.1";
        var value1 = "the.value.1";
        var key2 = "the.key.2";
        var value2 = "the.value.2";
        properties.put(key1, value1);
        properties.put(key2, value2);
        assertThat(properties).containsEntry(key1, value1);
        assertThat(properties).containsAllEntriesOf(Map.of(key1, value1, key2, value2));
        assertThat(properties).isNotEmpty();
    }
}
