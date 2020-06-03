package config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueFactory;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ReplaceProperty extends BaseTest {
    @Test
    public void withValue() {
        String path = "aaa.bbb";
        Config config = ConfigFactory.load("config/LoadFromPropertiesFile.properties")
                .withValue(path, ConfigValueFactory.fromAnyRef("88"));

        assertThat(config.getInt(path), equalTo(88));
    }
}
