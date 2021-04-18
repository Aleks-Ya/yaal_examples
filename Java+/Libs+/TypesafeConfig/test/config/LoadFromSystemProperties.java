package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoadFromSystemProperties extends BaseTest {

    @Test
    public void load() {
        var key = "magic.number";
        System.setProperty(key, "7");

        ConfigFactory.invalidateCaches();//Reset properties returned by ConfigFactory.systemProperties()

        var conf = ConfigFactory.load(ConfigFactory.systemProperties());
        assertThat(conf.getInt(key), equalTo(7));
    }
}
