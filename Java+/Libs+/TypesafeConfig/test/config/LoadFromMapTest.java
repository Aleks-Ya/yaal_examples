package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LoadFromMapTest extends BaseTest {

    @Test
    void load() {
        var key = "magic.number";
        var map = Map.of(key, "7");
        var conf = ConfigFactory.parseMap(map);
        assertThat(conf.getInt(key)).isEqualTo(7);
    }
}
