package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoadFromMap extends BaseTest {

    @Test
    void load() {
        var key = "magic.number";
        var map = Map.of(key, "7");
        var conf = ConfigFactory.parseMap(map);
        assertThat(conf.getInt(key), equalTo(7));
    }
}
