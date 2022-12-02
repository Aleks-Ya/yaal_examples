package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Выбрать из строки подстроку, соответствующую регулярному выражению, и распечатать ее.
 */
class SubstringTest {
    private static final String SOURCE = "GET /abba HTTP/1.1";
    private static final String EXPECTED = "/abba";

    @Test
    void realization1() {
        var p = Pattern.compile("^[^\\s]*\\s([^\\s]*)\\s.*$");
        var m = p.matcher(SOURCE);
        assertThat(m.matches()).isTrue();//Без вызова matches() упадет ошибка
        assertThat(m.group(1)).isEqualTo(EXPECTED);
    }

    @Test
    void realization2() {
        var p = Pattern.compile("^[^\\s]*\\s([^\\s]*)\\s.*$");
        var m = p.matcher(SOURCE);
        assertThat(m.find()).isTrue();
        assertThat(m.group(1)).isEqualTo(EXPECTED);
    }

    @Test
    void realization3() {
        var splitted = SOURCE.split(" ");
        assertThat(splitted[1]).isEqualTo(EXPECTED);
    }
}