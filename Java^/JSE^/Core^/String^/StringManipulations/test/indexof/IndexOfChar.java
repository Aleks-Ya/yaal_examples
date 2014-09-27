package indexof;

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
        assertEquals(1, "abc".indexOf('b'));
    }

    /**
     * Символ не найден.
     */
    @Test
    public void notFound() {
        assertEquals(-1, "abc".indexOf('d'));
    }

    /**
     * Поиск с заданной позиции.
     */
    @Test
    public void from() {
        assertEquals(3, "adcd".indexOf('d',2));
    }
}