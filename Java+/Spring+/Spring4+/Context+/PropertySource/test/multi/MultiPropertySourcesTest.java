package multi;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Load properties from several {@link PropertySource}.
 */
@ContextConfiguration(classes = MultiConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
class MultiPropertySourcesTest {
    @Autowired
    private Environment env;

    @Test
    void environmentFromClasspath() {
        assertThat(env.getProperty("star.name")).isEqualTo("Sun");
        assertThat(env.getProperty("planet.name")).isEqualTo("Earth");
    }
}