package lang.string.string.intern;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Интернирование строк.
 */
class InternTest {

    /**
     * Строки, созданные конструктором, НЕ интернируются.
     */
    @Test
    void constructor() {
        var s1 = new String("a");
        var s2 = new String("a");
        assertThat(s1 == s2).isFalse();
        assertThat(s1.equals(s2)).isTrue();
    }

    /**
     * Строки, созданные из литералов, интернируются.
     */
    @Test
    void literal() {
        var s1 = "a";
        var s2 = "a";
        assertThat(s1 == s2).isTrue();
        assertThat(s1.equals(s2)).isTrue();
    }

    /**
     * Интернирование строк, созданных конструктором.
     */
    @Test
    void intern() {
        var s1 = new String("a");
        var s2 = new String("a");

        var i1 = s1.intern();
        var i2 = s2.intern();

        assertThat(i1 == i2).isTrue();
        assertThat(i1.equals(i2)).isTrue();

        assertThat(s1 == s2).isFalse();
        assertThat(s1.equals(s2)).isTrue();
    }
}