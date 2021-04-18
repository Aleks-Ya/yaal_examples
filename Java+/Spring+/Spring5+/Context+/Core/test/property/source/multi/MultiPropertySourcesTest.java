package property.source.multi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Load properties from several {@link PropertySource}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MultiConfig.class)
public class MultiPropertySourcesTest {
    @Autowired
    private Environment env;

    @Test
    public void environmentFromClasspath() {
        assertThat(env.getProperty("star.name"), equalTo("Sun"));
        assertThat(env.getProperty("planet.name"), equalTo("Earth"));
    }
}