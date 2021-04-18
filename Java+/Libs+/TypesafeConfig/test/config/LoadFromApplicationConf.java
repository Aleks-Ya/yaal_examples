package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Read config from "application.conf" in the classpath.
 */
public class LoadFromApplicationConf extends BaseTest {

    @Test
    public void load() {
        var conf = ConfigFactory.load();
        var act = conf.getString("property.from.app.conf");
        assertThat(act, equalTo("hello"));
    }
}
