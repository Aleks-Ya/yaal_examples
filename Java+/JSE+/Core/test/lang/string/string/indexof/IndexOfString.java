package lang.string.string.indexof;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Поиск подстроки с помощью String#indexOf(String).
 */
class IndexOfString {

    /**
     * Подстрока найдена.
     */
    @Test
    void found() {
        assertEquals(1, "abcd".indexOf("bc"));
    }

    /**
     * Поиск с заданной позиции.
     */
    @Test
    void from() {
        assertEquals(4, "abcdab".indexOf("ab", 1));
    }

    /**
     * Подстрока не найдена.
     */
    @Test
    void notFound() {
        assertEquals(-1, "abcd".indexOf("bd"));
    }

    /**
     * Поиск подстроки null.
     */
    @Test
    void findNull() {
        assertThrows(NullPointerException.class, () -> "abc".indexOf(null));
    }

    /**
     * Поиск пустой подстроки.
     */
    @Test
    void empty() {
        assertEquals(0, "abc".indexOf(""));
        assertEquals(1, "abc".indexOf("", 1));
        assertEquals(2, "abc".indexOf("", 2));
        assertEquals(3, "abc".indexOf("", 3));
        assertEquals(3, "abc".indexOf("", 4));
        assertEquals(3, "abc".indexOf("", 40));
    }
}