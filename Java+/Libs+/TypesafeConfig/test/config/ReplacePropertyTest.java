package config;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReplacePropertyTest extends BaseTest {
    @Test
    void withValue() {
        var path = "aaa.bbb";
        var config = ConfigFactory.load("config/LoadFromPropertiesFile.properties")
                .withValue(path, ConfigValueFactory.fromAnyRef("88"));

        assertThat(config.getInt(path)).isEqualTo(88);
    }
}
