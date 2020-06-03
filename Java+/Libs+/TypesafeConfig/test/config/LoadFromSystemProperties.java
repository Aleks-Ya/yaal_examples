package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoadFromSystemProperties extends BaseTest {

    @Test
    public void load() {
        String key = "magic.number";
        System.setProperty(key, "7");

        ConfigFactory.invalidateCaches();//Reset properties returned by ConfigFactory.systemProperties()

        Config conf = ConfigFactory.load(ConfigFactory.systemProperties());
        assertThat(conf.getInt(key), equalTo(7));
    }
}
