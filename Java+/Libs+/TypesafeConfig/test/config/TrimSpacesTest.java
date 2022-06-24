package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class TrimSpacesTest extends BaseTest {
    @Test
    void trimSpacesFromProperties() {
        var key1 = "single.space";
        var key2 = "twice.space";
        var properties = new Properties();
        properties.put(key1, " ");
        properties.put(key2, "  ");
        var conf = ConfigFactory.parseProperties(properties);
        assertThat(conf.getString(key1)).isEqualTo(" ");
        assertThat(conf.getString(key2)).isEqualTo("  ");
    }

    @Test
    void trimSpacesFromFile() {
        var conf = ConfigFactory.load("config/LoadFromPropertiesFile_trimSpacesFromFile.properties");
        assertThat(conf.getString("aaa.bbb")).isEqualTo("77");
        assertThat(conf.getString("ggg.ttt")).isEqualTo(" ");
    }
}
