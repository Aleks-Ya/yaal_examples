package util.regex;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Предопределенные символьные классы: \d
 */
class PredefinedCharacterClassesTest {
    @Test
    void digits() {
        assertThat("a567b".matches("a\\d+b")).isTrue();
    }

    /**
     * Punctuation characters
     */
    @Test
    void punctuation() {
        assertThat("-!\"\\#$%&'()*+,./:;<=>?@[]^_`{|}~".matches("\\p{Punct}{32}")).isTrue();
    }

}
