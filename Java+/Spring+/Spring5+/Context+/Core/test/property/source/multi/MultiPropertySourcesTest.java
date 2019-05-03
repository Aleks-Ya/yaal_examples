package property.source.multi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Load properties from several {@link PropertySource}.
 */
@ContextConfiguration(classes = MultiConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MultiPropertySourcesTest {
    @Autowired
    private Environment env;

    @Test
    public void environmentFromClasspath() {
        assertThat(env.getProperty("star.name"), equalTo("Sun"));
        assertThat(env.getProperty("planet.name"), equalTo("Earth"));
    }
}