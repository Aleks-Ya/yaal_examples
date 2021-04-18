package util.regex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Предопределенные символьные классы: \d
 */
public class PredefinedCharacterClassesTest {
    @Test
    public void digits() {
        assertTrue("a567b".matches("a\\d+b"));
    }

    /**
     * Punctuation characters
     */
    @Test
    public void punctuation() {
        assertTrue("-!\"\\#$%&'()*+,./:;<=>?@[]^_`{|}~".matches("\\p{Punct}{32}"));
    }

}
