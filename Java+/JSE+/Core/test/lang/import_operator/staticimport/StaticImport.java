package staticimport;

import org.junit.jupiter.api.Test;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static staticimport.other.OtherPackage.number;

/**
 * Статический импорт.
 */
public class StaticImport {
    @Test
    public void normal() throws Exception {
        assertTrue(1 < MAX_VALUE);
        assertEquals("abc", format("%s", "abc"));
    }

    /**
     * Конфликт имен поля и метода нет: метод дополняется скобками.
     */
    @Test
    public void conflict() throws Exception {
        assertEquals(2, number());
        assertEquals(1, number);
    }
}
