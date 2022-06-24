package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoadFromSystemPropertiesTest extends BaseTest {

    @Test
    void load() {
        var key = "magic.number";
        System.setProperty(key, "7");

        ConfigFactory.invalidateCaches();//Reset properties returned by ConfigFactory.systemProperties()

        var conf = ConfigFactory.load(ConfigFactory.systemProperties());
        assertThat(conf.getInt(key)).isEqualTo(7);
    }
}
