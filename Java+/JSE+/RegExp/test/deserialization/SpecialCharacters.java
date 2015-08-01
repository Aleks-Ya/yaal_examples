package deserialization;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Поиск специальных символов.
 */
public class SpecialCharacters {

    @Test
    public void ampersand() {
        final String source = "concert $Einaudi ludovico einaudi today ";
        Pattern p = Pattern.compile("\\$Einaudi");
        Matcher m = p.matcher(source);
        assertTrue(m.find());
        assertEquals("$Einaudi", m.group());
        assertFalse(m.find());
    }

    @Test
    public void brace() {
        final String source = "concert {Einaudi} ludovico einaudi today ";
        Pattern p = Pattern.compile("\\{Einaudi\\}");
        Matcher m = p.matcher(source);
        assertTrue(m.find());
        assertEquals("{Einaudi}", m.group());
        assertFalse(m.find());
    }

    @Test
    public void wordInBraces() {
        final String source = "concert {Einaudi} ludovico einaudi today ";
        Pattern p = Pattern.compile("\\{\\w*\\}");
        Matcher m = p.matcher(source);
        assertTrue(m.find());
        assertEquals("{Einaudi}", m.group());
        assertFalse(m.find());
    }
}