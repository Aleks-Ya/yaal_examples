package soft_assertions;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

/**
 * Required execute SoftAssertions#assertAll()
 */
public class AssertAllTest {

    @Test(expected = org.assertj.core.api.SoftAssertionError.class)
    public void assertAll() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat("a").isEqualTo("b");
        softly.assertThat("a").containsSequence("b");
        softly.assertAll();
    }
}
