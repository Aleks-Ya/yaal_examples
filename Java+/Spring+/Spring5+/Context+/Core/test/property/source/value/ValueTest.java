package property.source.value;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Take properties using {@link Value} annotation.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ValueConfig.class)
public class ValueTest {

    @Value("${planet.name}")
    private String planetName;

    @Test
    public void value() {
        assertThat(planetName, equalTo("Venus"));
    }
}