package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Поиск нескольких вхождений регулярного выражения в строку.
 */
class SubstringSearchTest {

    @Test
    void main() {
        var source = "concert Einaudi ludovico einaudi today ";
        var p = Pattern.compile("[Ee]inaudi");
        var m = p.matcher(source);
        assertTrue(m.find());
        assertEquals("Einaudi", m.group());
        assertTrue(m.find());
        assertEquals("einaudi", m.group());
        assertFalse(m.find());
    }
}