package assertj.soft_assertions;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.error.AssertJMultipleFailuresError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Required execute SoftAssertions#assertAll()
 */
class AssertAllTest {

    @Test
    void assertAll() {
        assertThrows(AssertJMultipleFailuresError.class, () -> {
            var softly = new SoftAssertions();
            softly.assertThat("a").isEqualTo("b");
            softly.assertThat("a").containsSequence("b");
            softly.assertAll();
        });
    }
}
