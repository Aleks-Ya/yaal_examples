package config;

import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ReplaceProperty extends BaseTest {
    @Test
    void withValue() {
        var path = "aaa.bbb";
        var config = ConfigFactory.load("config/LoadFromPropertiesFile.properties")
                .withValue(path, ConfigValueFactory.fromAnyRef("88"));

        assertThat(config.getInt(path), equalTo(88));
    }
}
