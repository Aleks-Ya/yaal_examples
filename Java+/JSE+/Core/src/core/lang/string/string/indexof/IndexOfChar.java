package core.lang.string.string.indexof;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Поиск символа в строке с помощью String#indexOf(Char).
 */
public class IndexOfChar {

    /**
     * Символ найден.
     */
    @Test
    public void found() {
        Assert.assertEquals(1, "abc".indexOf('b'));
    }

    /**
     * Символ не найден.
     */
    @Test
    public void notFound() {
        Assert.assertEquals(-1, "abc".indexOf('d'));
    }

    /**
     * Поиск с заданной позиции.
     */
    @Test
    public void from() {
        Assert.assertEquals(3, "adcd".indexOf('d',2));
    }
}