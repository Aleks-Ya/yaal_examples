package assertj.soft_assertions;

import org.assertj.core.api.SoftAssertionError;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Required execute SoftAssertions#assertAll()
 */
public class AssertAllTest {

    @Test
    public void assertAll() {
        assertThrows(SoftAssertionError.class, () -> {
            SoftAssertions softly = new SoftAssertions();
            softly.assertThat("a").isEqualTo("b");
            softly.assertThat("a").containsSequence("b");
            softly.assertAll();
        });
    }
}
