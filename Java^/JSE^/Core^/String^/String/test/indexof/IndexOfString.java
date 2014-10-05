package indexof;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Поиск подстроки с помощью String#indexOf(String).
 */
public class IndexOfString {

    /**
     * Подстрока найдена.
     */
    @Test
    public void found() {
        assertEquals(1, "abcd".indexOf("bc"));
    }

    /**
     * Поиск с заданной позиции.
     */
    @Test
    public void from() {
        assertEquals(4, "abcdab".indexOf("ab", 1));
    }

    /**
     * Подстрока не найдена.
     */
    @Test
    public void notFound() {
        assertEquals(-1, "abcd".indexOf("bd"));
    }

    /**
     * Поиск подстроки null.
     */
    @Test(expected = NullPointerException.class)
    public void findNull() {
        assertEquals(-1, "abc".indexOf(null));
    }

    /**
     * Поиск пустой подстроки.
     */
    @Test
    public void empty() {
        assertEquals(0, "abc".indexOf(""));
    }
}