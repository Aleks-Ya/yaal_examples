package soft_assertions;

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

/**
 * Required execute SoftAssertions#assertAll()
 */
public class AssertAllTest {

    @Test
    public void assertAll() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat("a").isEqualTo("b");
        softly.assertThat("a").containsSequence("b");
        softly.assertAll();
        //it must fail
    }
}
