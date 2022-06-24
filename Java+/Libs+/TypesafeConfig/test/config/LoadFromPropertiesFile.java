package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoadFromPropertiesFile extends BaseTest {
    @Test
    void loadFromResource() {
        var conf = ConfigFactory.load("config/LoadFromPropertiesFile.properties");

        var act = conf.getInt("aaa.bbb");
        assertThat(act, equalTo(77));
    }
}
