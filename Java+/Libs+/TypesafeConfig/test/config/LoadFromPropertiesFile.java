package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoadFromPropertiesFile {
    @Test
    public void loadFromResource() {
        Config conf = ConfigFactory.load("config/LoadFromPropertiesFile.properties");
        int act = conf.getInt("aaa.bbb");
        assertThat(act, equalTo(77));
    }
}
