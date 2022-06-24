package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class LoadFromPropertiesObjectTest extends BaseTest {

    @Test
    void load() {
        var key = "magic.number";
        var properties = new Properties();
        properties.put(key, "7");
        var conf = ConfigFactory.parseProperties(properties);
        assertThat(conf.getInt(key)).isEqualTo(7);
    }
}
