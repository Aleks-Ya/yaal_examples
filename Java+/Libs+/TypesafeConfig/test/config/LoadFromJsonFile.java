package config;

import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoadFromJsonFile extends BaseTest {
    @Test
    public void loadFromJson() {
        var conf = ConfigFactory.load("config/LoadFromJsonFile.json");
        assertThat(conf.getString("parameters.host"), equalTo("localhost"));
        assertThat(conf.getInt("parameters.port"), equalTo(8080));
        assertThat(conf.hasPath("version.major"), equalTo(false));
        assertThat(conf.hasPath("version.minor"), equalTo(false));
    }
}
