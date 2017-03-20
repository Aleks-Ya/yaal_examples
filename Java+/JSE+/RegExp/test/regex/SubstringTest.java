package regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Выбрать из строки подстроку, соответствующую регулярному выражению, и распечатать ее.
 */
public class SubstringTest {
    private static final String SOURCE = "GET /abba HTTP/1.1";
    private static final String EXPECTED = "/abba";

    @Test
    public void realization1() {
        Pattern p = Pattern.compile("^[^\\s]*\\s([^\\s]*)\\s.*$");
        Matcher m = p.matcher(SOURCE);
        assertTrue(m.matches());//Без вызова matches() упадет ошибка
        assertEquals(EXPECTED, m.group(1));
    }

    @Test
    public void realization2() {
        Pattern p = Pattern.compile("^[^\\s]*\\s([^\\s]*)\\s.*$");
        Matcher m = p.matcher(SOURCE);
        assertTrue(m.find());
        assertEquals(EXPECTED, m.group(1));
    }

    @Test
    public void realization3() {
        String[] splitted = SOURCE.split(" ");
        assertEquals(EXPECTED, splitted[1]);
    }
}