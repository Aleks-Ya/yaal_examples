package lang.string.string.intern;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Интернирование строк.
 */
public class Intern {

    /**
     * Строки, созданные конструктором, НЕ интернируются.
     */
    @Test
    public void constructor() {
        String s1 = new String("a");
        String s2 = new String("a");
        assertFalse(s1 == s2);
        assertTrue(s1.equals(s2));
    }

    /**
     * Строки, созданные из литералов, интернируются.
     */
    @Test
    public void literal() {
        String s1 = "a";
        String s2 = "a";
        assertTrue(s1 == s2);
        assertTrue(s1.equals(s2));
    }

    /**
     * Интернирование строк, созданных конструктором.
     */
    @Test
    public void intern() {
        String s1 = new String("a");
        String s2 = new String("a");

        String i1 = s1.intern();
        String i2 = s2.intern();

        assertTrue(i1 == i2);
        assertTrue(i1.equals(i2));

        assertFalse(s1 == s2);
        assertTrue(s1.equals(s2));
    }
}