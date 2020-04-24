package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoadFromSystemProperties {

    @Test
    public void load() {
        String key = "foo.bar";
        System.setProperty(key, "7");

        ConfigFactory.invalidateCaches();
        Config conf = ConfigFactory.load();
        int act = conf.getInt(key);

        assertThat(act, equalTo(7));
    }
}
