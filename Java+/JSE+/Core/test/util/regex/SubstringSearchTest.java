package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Поиск нескольких вхождений регулярного выражения в строку.
 */
public class SubstringSearchTest {

    @Test
    public void main() {
        final String source = "concert Einaudi ludovico einaudi today ";
        Pattern p = Pattern.compile("[Ee]inaudi");
        Matcher m = p.matcher(source);
        assertTrue(m.find());
        assertEquals("Einaudi", m.group());
        assertTrue(m.find());
        assertEquals("einaudi", m.group());
        assertFalse(m.find());
    }
}