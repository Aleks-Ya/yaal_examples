package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(m.matches());//Без вызова matches() упадет ошибка
        assertEquals(EXPECTED, m.group(1));
    }

    @Test
    void realization2() {
        var p = Pattern.compile("^[^\\s]*\\s([^\\s]*)\\s.*$");
        var m = p.matcher(SOURCE);
        assertTrue(m.find());
        assertEquals(EXPECTED, m.group(1));
    }

    @Test
    void realization3() {
        var splitted = SOURCE.split(" ");
        assertEquals(EXPECTED, splitted[1]);
    }
}