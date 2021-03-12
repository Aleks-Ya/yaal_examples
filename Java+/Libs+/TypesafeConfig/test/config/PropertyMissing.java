package config;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThrows;

public class PropertyMissing extends BaseTest {

    @Test
    public void missingException() {
        var conf = ConfigFactory.empty();
        var e = assertThrows(ConfigException.Missing.class, () -> conf.getString("absent.key"));
        assertThat(e.getMessage(), equalTo("empty config: No configuration setting found for key 'absent'"));
    }

    @Test
    public void checkPropertyExists() {
        var presentKey = "present.key";
        var absentKey = "absent.key";
        var conf = ConfigFactory.parseMap(Map.of(presentKey, "abc"));

        assertThat(conf.hasPath(absentKey), equalTo(false));
        assertThat(conf.hasPathOrNull(absentKey), equalTo(false));

        assertThat(conf.hasPath(presentKey), equalTo(true));
        assertThat(conf.hasPathOrNull(presentKey), equalTo(true));
    }
}
