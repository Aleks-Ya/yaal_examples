import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Примеры позиционной проверки.
 */
public class LookAround {

    /**
     * Строка не должна заканчиваться словом "fail".
     */
    @Test
    public void notEndWith() {
        Pattern p = Pattern.compile("^.*(?<!hede)$");
        assertTrue(p.matcher("hede obsolete").matches());
        assertTrue(p.matcher("obsolete hede obsolete").matches());
        assertFalse(p.matcher("obsolete hede").matches());
    }

    /**
     * Проверяем, что URL заканчивается строкой "/success" или ."/success/"
     * (позитивная опережающая проверка).
     */
    @Test
    public void endWith() {
        Pattern p = Pattern.compile(".*(?=/success/?$).*");
        assertFalse(p.matcher("/app/check").matches());
        assertFalse(p.matcher("/app/success/check").matches());
        assertTrue(p.matcher("/app/check/success").matches());
        assertTrue(p.matcher("/app/check/success/").matches());
    }

    /**
     * Строка не должна содержать слово "fail".
     */
    @Test
    public void notContain() {
        Pattern p = Pattern.compile("^((?!fail).)*$");
        assertTrue(p.matcher("/app/check/success").matches());
        assertFalse(p.matcher("/fail/check").matches());
        assertFalse(p.matcher("fail/app/success/check").matches());
        assertFalse(p.matcher("/app/success/fail").matches());
    }

    /**
     * Строка не должна начинаться со слова "fail".
     */
    @Test
    public void notStartWith() {
        Pattern p = Pattern.compile("^(?!fail).*$");
        assertTrue(p.matcher("/app/check/success").matches());
        assertTrue(p.matcher("/fail/check").matches());
        assertTrue(p.matcher("/app/success/fail").matches());
        assertFalse(p.matcher("fail/app/success/check").matches());
    }
}