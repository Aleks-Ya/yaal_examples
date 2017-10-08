package core.lang.import_operator.staticimport;

import core.lang.import_operator.staticimport.other.OtherPackage;
import org.junit.Test;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static core.lang.import_operator.staticimport.other.OtherPackage.number;

/**
 * Статический импорт.
 */
public class StaticImport {
    @Test
    public void normal() throws Exception {
        Assert.assertTrue(1 < MAX_VALUE);
        Assert.assertEquals("abc", format("%s", "abc"));
    }

    /**
     * Конфликт имен поля и метода нет: метод дополняется скобками.
     */
    @Test
    public void conflict() throws Exception {
        Assert.assertEquals(2, OtherPackage.number());
        Assert.assertEquals(1, OtherPackage.number);
    }
}
