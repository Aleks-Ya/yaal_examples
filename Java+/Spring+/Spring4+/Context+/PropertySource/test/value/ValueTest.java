package value;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Take properties using {@link Value} annotation.
 */
@ContextConfiguration(classes = ValueConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ValueTest {

    @Value("${planet.name}")
    private String planetName;

    @Test
    void value() {
        assertThat(planetName, equalTo("Venus"));
    }
}