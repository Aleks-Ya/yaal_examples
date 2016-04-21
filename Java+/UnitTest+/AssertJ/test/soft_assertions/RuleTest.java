package soft_assertions;

import org.assertj.core.api.JUnitSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Rule;
import org.junit.Test;

/**
 * Use JUnitSoftAssertions rule (without execute SoftAssertions#assertAll()).
 */

public class RuleTest {
    @Rule
    public final JUnitSoftAssertions softly = new JUnitSoftAssertions();

    @Test
    public void rule() {
        softly.assertThat("a").isEqualTo("b");
        softly.assertThat("a").containsSequence("b");
        //it must filed
    }
}
