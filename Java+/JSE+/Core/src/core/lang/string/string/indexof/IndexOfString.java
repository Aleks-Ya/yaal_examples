package core.lang.string.string.indexof;

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
        Assert.assertEquals(1, "abcd".indexOf("bc"));
    }

    /**
     * Поиск с заданной позиции.
     */
    @Test
    public void from() {
        Assert.assertEquals(4, "abcdab".indexOf("ab", 1));
    }

    /**
     * Подстрока не найдена.
     */
    @Test
    public void notFound() {
        Assert.assertEquals(-1, "abcd".indexOf("bd"));
    }

    /**
     * Поиск подстроки null.
     */
    @Test(expected = NullPointerException.class)
    public void findNull() {
        Assert.assertEquals(-1, "abc".indexOf(null));
    }

    /**
     * Поиск пустой подстроки.
     */
    @Test
    public void empty() {
        Assert.assertEquals(0, "abc".indexOf(""));
        Assert.assertEquals(1, "abc".indexOf("", 1));
        Assert.assertEquals(2, "abc".indexOf("", 2));
        Assert.assertEquals(3, "abc".indexOf("", 3));
        Assert.assertEquals(3, "abc".indexOf("", 4));
        Assert.assertEquals(3, "abc".indexOf("", 40));
    }
}