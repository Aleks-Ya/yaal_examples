package assertj.builtin;

import org.assertj.core.presentation.StandardRepresentation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MessageTest {
    @Test
    void standardMessage() {
        assertThatThrownBy(() -> assertThat("abc").isEqualTo("ABC"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("""
                                                
                        expected: "ABC"
                         but was: "abc\"""");
    }

    @Test
    void as() {
        assertThatThrownBy(() -> assertThat("abc").as("Context").isEqualTo("ABC"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("""
                        [Context]\s
                        expected: "ABC"
                         but was: "abc\"""");
    }

    @Test
    void describedAs() {
        assertThatThrownBy(() -> assertThat("abc").describedAs("Context").isEqualTo("ABC"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("""
                        [Context]\s
                        expected: "ABC"
                         but was: "abc\"""");
    }

    @Test
    void asAndDescribedAs() {
        assertThatThrownBy(() -> assertThat("abc")
                .as("As text")
                .describedAs("Described As")
                .isEqualTo("ABC")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("""
                        [Described As]\s
                        expected: "ABC"
                         but was: "abc\"""");
    }

    @Test
    void overridingErrorMessage() {
        assertThatThrownBy(() -> assertThat("abc")
                .overridingErrorMessage("Error message")
                .isEqualTo("ABC")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("Error message");
    }

    @Test
    void withFailMessage() {
        assertThatThrownBy(() -> assertThat("abc").withFailMessage("Fail message").isEqualTo("ABC"))
                .isInstanceOf(AssertionError.class)
                .hasMessage("Fail message");
    }

    @Test
    void withRepresentation() {
        assertThatThrownBy(() -> assertThat("abc")
                .withRepresentation(new StandardRepresentation() {
                    @Override
                    protected String toStringOf(String s) {
                        return "<<" + s + ">>";
                    }
                })
                .isEqualTo("ABC")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("""
                                                
                        expected: <<ABC>>
                         but was: <<abc>>""");
    }

    @Test
    void asAndWithRepresentation() {
        assertThatThrownBy(() -> assertThat("abc")
                .as("Context")
                .withRepresentation(new StandardRepresentation() {
                    @Override
                    protected String toStringOf(String s) {
                        return "<<" + s + ">>";
                    }
                })
                .isEqualTo("ABC")
        )
                .isInstanceOf(AssertionError.class)
                .hasMessage("""
                        [Context]\s
                        expected: <<ABC>>
                         but was: <<abc>>""");
    }

}
