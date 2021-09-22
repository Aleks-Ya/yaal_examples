package lang.string.string.indexof;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Поиск символа в строке с помощью String#indexOf(Char).
 */
class IndexOfChar {

    /**
     * Символ найден.
     */
    @Test
    void found() {
        assertEquals(1, "abc".indexOf('b'));
    }

    /**
     * Символ не найден.
     */
    @Test
    void notFound() {
        assertEquals(-1, "abc".indexOf('d'));
    }

    /**
     * Поиск с заданной позиции.
     */
    @Test
    void from() {
        assertEquals(3, "adcd".indexOf('d',2));
    }
}