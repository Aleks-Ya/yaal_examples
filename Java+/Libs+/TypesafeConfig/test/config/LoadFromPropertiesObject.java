package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoadFromPropertiesObject extends BaseTest {

    @Test
    public void load() {
        String key = "magic.number";
        Properties properties = new Properties();
        properties.put(key, "7");
        Config conf = ConfigFactory.parseProperties(properties);
        assertThat(conf.getInt(key), equalTo(7));
    }
}