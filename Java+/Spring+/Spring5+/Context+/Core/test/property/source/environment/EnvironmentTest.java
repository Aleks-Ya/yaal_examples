package property.source.environment;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Take properties from {@link Environment}.
 */
@ContextConfiguration(classes = EnvironmentConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EnvironmentTest {
    @Autowired
    private Environment env;

    @Test
    public void environmentFromClasspath() {
        assertThat(env.getProperty("planet.name"), equalTo("Mars"));
    }
}