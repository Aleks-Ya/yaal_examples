package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Read config from "application.conf" in the classpath.
 */
class LoadFromApplicationConfTest extends BaseTest {

    @Test
    void load() {
        var conf = ConfigFactory.load();
        var act = conf.getString("property.from.app.conf");
        assertThat(act).isEqualTo("hello");
    }
}
