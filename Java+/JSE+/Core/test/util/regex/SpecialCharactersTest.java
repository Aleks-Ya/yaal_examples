package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Поиск специальных символов.
 */
class SpecialCharactersTest {

    @Test
    void ampersand() {
        var source = "concert $Einaudi ludovico einaudi today ";
        var p = Pattern.compile("\\$Einaudi");
        var m = p.matcher(source);
        assertTrue(m.find());
        assertEquals("$Einaudi", m.group());
        assertFalse(m.find());
    }

    @Test
    void brace() {
        var source = "concert {Einaudi} ludovico einaudi today ";
        var p = Pattern.compile("\\{Einaudi}");
        var m = p.matcher(source);
        assertTrue(m.find());
        assertEquals("{Einaudi}", m.group());
        assertFalse(m.find());
    }

    @Test
    void wordInBraces() {
        var source = "concert {Einaudi} ludovico einaudi today ";
        var p = Pattern.compile("\\{\\w*}");
        var m = p.matcher(source);
        assertTrue(m.find());
        assertEquals("{Einaudi}", m.group());
        assertFalse(m.find());
    }

    /**
     * Fix "PatternSyntaxException: Unclosed character class near index".
     */
    @Test
    void unclosedCharacterClass() {
        var text = "abc main(java.lang.String[]) ghi";
        var regex = "main(java.lang.String[])";
        var quoted = Pattern.quote(regex);
        //noinspection ResultOfMethodCallIgnored
        Pattern.compile(quoted);// additional check that queted reqex can be compiled
        assertThat(text.replaceAll(quoted, "def"), equalTo("abc def ghi"));
    }
}