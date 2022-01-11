package property.source.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Take properties using {@link Value} annotation.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ValueConfig.class)
class ValueTest {

    @Value("${planet.name}")
    private String planetName;

    @Test
    void value() {
        assertThat(planetName).isEqualTo("Venus");
    }
}