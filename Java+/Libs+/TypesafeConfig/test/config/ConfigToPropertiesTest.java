package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigToPropertiesTest extends BaseTest {
    private static Properties configToProperties(Config config) {
        var properties = new Properties();
        config.entrySet().forEach(entry -> properties.put(entry.getKey(), entry.getValue().unwrapped()));
        return properties;
    }

    @Test
    void configToProperties() {
        var expProperties = new Properties();
        expProperties.put("a", "1");
        var conf = ConfigFactory.parseProperties(expProperties);
        var actProperties = configToProperties(conf);
        assertThat(actProperties).isEqualTo(expProperties);
    }
}
