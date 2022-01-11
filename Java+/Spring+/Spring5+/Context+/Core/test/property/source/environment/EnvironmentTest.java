package property.source.environment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Take properties from {@link Environment}.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = EnvironmentConfig.class)
class EnvironmentTest {
    @Autowired
    private Environment env;

    @Test
    void environmentFromClasspath() {
        assertThat(env.getProperty("planet.name")).isEqualTo("Mars");
    }
}