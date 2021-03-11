package config;

import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoadFromJsonFile extends BaseTest {
    @Test
    public void loadFromJson() {
        var conf = ConfigFactory.load("config/LoadFromJsonFile.json");

        var host = conf.getString("parameters.host");
        var port = conf.getInt("parameters.port");

        assertThat(host, equalTo("localhost"));
        assertThat(port, equalTo(8080));
    }
}
