package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MergeConfigsTest extends BaseTest {
    @Test
    void merge() {
        System.setProperty("system.number", "1");
        ConfigFactory.invalidateCaches();//Reset properties returned by ConfigFactory.systemProperties()
        var systemPropertiesConf = ConfigFactory.load(ConfigFactory.systemProperties());

        var defaultConf = ConfigFactory.load().withFallback(systemPropertiesConf);

        var propertiesFileConf = ConfigFactory.load("config/LoadFromPropertiesFile.properties")
                .withFallback(defaultConf);

        assertThat(propertiesFileConf.getInt("system.number")).isEqualTo(1);
        assertThat(propertiesFileConf.getInt("aaa.bbb")).isEqualTo(77);
        assertThat(propertiesFileConf.getInt("magic.number")).isEqualTo(6);
    }

    @Test
    void atPath() {
        System.setProperty("system.number", "1");
        ConfigFactory.invalidateCaches();//Reset properties returned by ConfigFactory.systemProperties()
        var systemPropertiesConf = ConfigFactory.load(ConfigFactory.systemProperties()).atPath("sys");
        var propertiesFileConf = ConfigFactory.load("config/LoadFromPropertiesFile.properties")
                .withFallback(systemPropertiesConf);
        assertThat(propertiesFileConf.getInt("sys.system.number")).isEqualTo(1);
        assertThat(propertiesFileConf.getInt("aaa.bbb")).isEqualTo(77);
    }
}
