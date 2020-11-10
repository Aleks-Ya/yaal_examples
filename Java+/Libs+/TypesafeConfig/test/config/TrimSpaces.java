package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TrimSpaces extends BaseTest {
    @Test
    public void trimSpacesFromProperties() {
        String key1 = "single.space";
        String key2 = "twice.space";
        Properties properties = new Properties();
        properties.put(key1, " ");
        properties.put(key2, "  ");
        Config conf = ConfigFactory.parseProperties(properties);
        assertThat(conf.getString(key1), equalTo(" "));
        assertThat(conf.getString(key2), equalTo("  "));
    }

    @Test
    public void trimSpacesFromFile() {
        Config conf = ConfigFactory.load("config/LoadFromPropertiesFile_trimSpacesFromFile.properties");
        assertThat(conf.getString("aaa.bbb"), equalTo("77"));
        assertThat(conf.getString("ggg.ttt"), equalTo(" "));
    }
}
