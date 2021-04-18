package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ConfigToProperties extends BaseTest {
    @Test
    public void configToProperties() {
        var expProperties = new Properties();
        expProperties.put("a", "1");
        var conf = ConfigFactory.parseProperties(expProperties);
        var actProperties = configToProperties(conf);
        assertThat(actProperties, equalTo(expProperties));
    }

    private static Properties configToProperties(Config config) {
        var properties = new Properties();
        config.entrySet().forEach(entry -> properties.put(entry.getKey(), entry.getValue().unwrapped()));
        return properties;
    }
}
