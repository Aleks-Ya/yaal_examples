package assertj.builtin.soft_assertions;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Required execute SoftAssertions#assertAll()
 */
class AssertAllTest {

    @Test
    void assertAll() {
        assertThatThrownBy(() -> {
            var softly = new SoftAssertions();
            softly.assertThat("a").isEqualTo("b");
            softly.assertThat("a").containsSequence("b");
            softly.assertAll();
        }).hasMessage("""
                                
                Multiple Failures (2 failures)
                -- failure 1 --
                expected: "b"
                 but was: "a"
                at AssertAllTest.lambda$assertAll$0(AssertAllTest.java:17)
                -- failure 2 --
                Expecting actual:
                  "a"
                to contain:
                  "b"\s
                at AssertAllTest.lambda$assertAll$0(AssertAllTest.java:18)""");
    }

    @Test
    void as() {
        assertThatThrownBy(() -> {
            var softly = new SoftAssertions();
            var prefix = "A";
            softly.assertThat("a").as("%s %s", prefix, "EQUALS").isEqualTo("b");
            softly.assertThat("a").as("%s %s", prefix, "CONTAINS").containsSequence("b");
            softly.assertAll();
        }).hasMessage("""
                
                Multiple Failures (2 failures)
                -- failure 1 --
                [A EQUALS]\s
                expected: "b"
                 but was: "a"
                at AssertAllTest.lambda$as$1(AssertAllTest.java:40)
                -- failure 2 --
                [A CONTAINS]\s
                Expecting actual:
                  "a"
                to contain:
                  "b"\s
                at AssertAllTest.lambda$as$1(AssertAllTest.java:41)""");
    }
}
