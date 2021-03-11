package config;

import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MergeConfigs extends BaseTest {
    @Test
    public void loadFromResource() {
        System.setProperty("system.number", "1");
        ConfigFactory.invalidateCaches();//Reset properties returned by ConfigFactory.systemProperties()
        var systemPropertiesConf = ConfigFactory.load(ConfigFactory.systemProperties());

        var defaultConf = ConfigFactory.load().withFallback(systemPropertiesConf);

        var propertiesFileConf = ConfigFactory.load("config/LoadFromPropertiesFile.properties")
                .withFallback(defaultConf);

        assertThat(propertiesFileConf.getInt("system.number"), equalTo(1));
        assertThat(propertiesFileConf.getInt("aaa.bbb"), equalTo(77));
        assertThat(propertiesFileConf.getInt("magic.number"), equalTo(6));
    }
}
