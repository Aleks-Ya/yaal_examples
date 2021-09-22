package lang.import_operator.staticimport;

import org.junit.jupiter.api.Test;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.String.format;
import static lang.import_operator.staticimport.other.OtherPackage.number;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Статический импорт.
 */
class StaticImport {
    @Test
    void normal() {
        assertTrue(1 < MAX_VALUE);
        assertEquals("abc", format("%s", "abc"));
    }

    /**
     * Конфликт имен поля и метода нет: метод дополняется скобками.
     */
    @Test
    void conflict() {
        assertEquals(2, number());
        assertEquals(1, number);
    }
}
