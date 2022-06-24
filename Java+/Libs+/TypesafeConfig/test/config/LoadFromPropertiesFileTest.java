package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoadFromPropertiesFileTest extends BaseTest {
    @Test
    void loadFromResource() {
        var conf = ConfigFactory.load("config/LoadFromPropertiesFile.properties");
        var act = conf.getInt("aaa.bbb");
        assertThat(act).isEqualTo(77);
    }
}
