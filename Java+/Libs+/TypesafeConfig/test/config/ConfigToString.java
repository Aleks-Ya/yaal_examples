package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ConfigToString extends BaseTest {
    @Test
    public void configToString() {
        Config conf = ConfigFactory.load();
        String str = conf.toString();
        System.out.println(str);
    }
}
