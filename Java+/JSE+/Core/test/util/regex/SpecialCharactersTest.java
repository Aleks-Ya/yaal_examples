package util.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Поиск специальных символов.
 */
public class SpecialCharactersTest {

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

    /**
     * Fix "PatternSyntaxException: Unclosed character class near index".
     */
    @Test
    public void unclosedCharacterClass() {
        String text = "abc main(java.lang.String[]) ghi";
        String regex = "main(java.lang.String[])";
        String quoted = Pattern.quote(regex);
        //noinspection ResultOfMethodCallIgnored
        Pattern.compile(quoted);// additional check that queted reqex can be compiled
        assertThat(text.replaceAll(quoted, "def"), equalTo("abc def ghi"));
    }
}