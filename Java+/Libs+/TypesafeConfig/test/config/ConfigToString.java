package config;

import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.Test;

public class ConfigToString extends BaseTest {
    @Test
    void configToString() {
        var conf = ConfigFactory.load();
        var str = conf.toString();
        System.out.println(str);
    }
}
