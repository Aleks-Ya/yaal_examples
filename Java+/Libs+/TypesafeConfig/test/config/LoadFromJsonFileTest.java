package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class LoadFromJsonFileTest extends BaseTest {
    private static Config splitKeysByDots(Config conf) {
        var map = new HashMap<String, Object>();
        for (var entry : conf.entrySet()) {
            var key = entry.getKey().replace("\"", "");
            map.put(key, entry.getValue());
        }
        return ConfigFactory.parseMap(map);
    }

    @Test
    void loadFromJson() {
        var conf = ConfigFactory.load("config/LoadFromJsonFile.json");
        assertThat(conf.getString("parameters.host")).isEqualTo("localhost");
        assertThat(conf.getInt("parameters.port")).isEqualTo(8080);
        assertThat(conf.getInt("release.\"version.major\"")).isEqualTo(33);
        assertThat(conf.getInt("release.\"version.minor\"")).isEqualTo(77);
    }

    @Test
    void splitByDots() {
        var confOriginal = ConfigFactory.load("config/LoadFromJsonFile.json");
        var confSplit = splitKeysByDots(confOriginal);
        assertThat(confSplit.getString("parameters.host")).isEqualTo("localhost");
        assertThat(confSplit.getInt("parameters.port")).isEqualTo(8080);
        assertThat(confSplit.getInt("release.version.major")).isEqualTo(33);
        assertThat(confSplit.getInt("release.version.minor")).isEqualTo(77);
    }
}
