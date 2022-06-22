package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Поиск специальных символов.
 */
class SpecialCharactersTest {

    @Test
    void ampersand() {
        var source = "concert $Einaudi ludovico einaudi today ";
        var p = Pattern.compile("\\$Einaudi");
        var m = p.matcher(source);
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo("$Einaudi");
        assertThat(m.find()).isFalse();
    }

    @Test
    void brace() {
        var source = "concert {Einaudi} ludovico einaudi today ";
        var p = Pattern.compile("\\{Einaudi}");
        var m = p.matcher(source);
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo("{Einaudi}");
        assertThat(m.find()).isFalse();
    }

    @Test
    void wordInBraces() {
        var source = "concert {Einaudi} ludovico einaudi today ";
        var p = Pattern.compile("\\{\\w*}");
        var m = p.matcher(source);
        assertThat(m.find()).isTrue();
        assertThat(m.group()).isEqualTo("{Einaudi}");
        assertThat(m.find()).isFalse();
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
        assertThat(text.replaceAll(quoted, "def")).isEqualTo("abc def ghi");
    }
}