package config;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PropertyMissingTest extends BaseTest {

    @Test
    void missingException() {
        var conf = ConfigFactory.empty();
        var e = assertThrows(ConfigException.Missing.class, () -> conf.getString("absent.key"));
        assertThat(e.getMessage()).isEqualTo("empty config: No configuration setting found for key 'absent'");
    }

    @Test
    void checkPropertyExists() {
        var presentKey = "present.key";
        var absentKey = "absent.key";
        var conf = ConfigFactory.parseMap(Map.of(presentKey, "abc"));

        assertThat(conf.hasPath(absentKey)).isEqualTo(false);
        assertThat(conf.hasPathOrNull(absentKey)).isEqualTo(false);

        assertThat(conf.hasPath(presentKey)).isEqualTo(true);
        assertThat(conf.hasPathOrNull(presentKey)).isEqualTo(true);
    }
}
