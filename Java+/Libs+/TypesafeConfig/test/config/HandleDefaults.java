package config;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Docs: https://github.com/lightbend/config#how-to-handle-defaults
 */
public class HandleDefaults extends BaseTest {

    @Test
    void hasPath() {
        var conf = ConfigFactory.empty();
        var key = "absent.key";
        var defaultValue = 3;
        var value = conf.hasPath(key) ? conf.getNumber(key) : defaultValue;
        assertThat(value, equalTo(defaultValue));
    }

    @Test
    void withFallback() {
        var key = "absent.key";
        var defaultValue = 3;
        var defaultConfig = ConfigFactory.parseMap(Map.of(key, defaultValue));
        var conf = ConfigFactory.empty().withFallback(defaultConfig);
        var value = conf.getNumber(key);
        assertThat(value, equalTo(defaultValue));
    }

    @Test
    void configObject() {
        var key = "absent.key";
        var defaultValue = 3;
        var conf = ConfigFactory.empty();
        var configObject = conf.root();
        var value = configObject.getOrDefault(key, ConfigValueFactory.fromAnyRef(defaultValue)).unwrapped();
        assertThat(value, equalTo(defaultValue));
    }

    /**
     * Default value is specified in reference.conf.
     */
    @Test
    void reference() {
        var key = "absent.key.def";
        var defaultValue = 3;
        var conf = ConfigFactory.load();
        var value = conf.getInt(key);
        assertThat(value, equalTo(defaultValue));
    }
}
