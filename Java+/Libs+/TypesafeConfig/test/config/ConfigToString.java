package config;

import com.typesafe.config.ConfigFactory;
import org.junit.Test;

public class ConfigToString extends BaseTest {
    @Test
    public void configToString() {
        var conf = ConfigFactory.load();
        var str = conf.toString();
        System.out.println(str);
    }
}
