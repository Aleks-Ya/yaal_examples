package lang.string.string.indexof;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Поиск символа в строке с помощью String#indexOf(Char).
 */
class IndexOfCharTest {

    /**
     * Символ найден.
     */
    @Test
    void found() {
        assertThat("abc".indexOf('b')).isEqualTo(1);
    }

    /**
     * Символ не найден.
     */
    @Test
    void notFound() {
        assertThat("abc".indexOf('d')).isEqualTo(-1);
    }

    /**
     * Поиск с заданной позиции.
     */
    @Test
    void from() {
        assertThat("adcd".indexOf('d', 2)).isEqualTo(3);
    }
}