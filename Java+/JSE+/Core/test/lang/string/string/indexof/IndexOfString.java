package lang.string.string.indexof;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/**
 * Поиск подстроки с помощью String#indexOf(String).
 */
class IndexOfString {

    /**
     * Подстрока найдена.
     */
    @Test
    void found() {
        assertThat("abcd".indexOf("bc")).isEqualTo(1);
    }

    /**
     * Поиск с заданной позиции.
     */
    @Test
    void from() {
        assertThat("abcdab".indexOf("ab", 1)).isEqualTo(4);
    }

    /**
     * Подстрока не найдена.
     */
    @Test
    void notFound() {
        assertThat("abcd".indexOf("bd")).isEqualTo(-1);
    }

    /**
     * Поиск подстроки null.
     */
    @Test
    void findNull() {
        assertThatThrownBy(() -> "abc".indexOf(null)).isInstanceOf(NullPointerException.class);
    }

    /**
     * Поиск пустой подстроки.
     */
    @Test
    void empty() {
        assertThat("abc".indexOf("")).isEqualTo(0);
        assertThat("abc".indexOf("", 1)).isEqualTo(1);
        assertThat("abc".indexOf("", 2)).isEqualTo(2);
        assertThat("abc".indexOf("", 3)).isEqualTo(3);
        assertThat("abc".indexOf("", 4)).isEqualTo(3);
        assertThat("abc".indexOf("", 40)).isEqualTo(3);
    }
}