package lang.string.string.exercise;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Подсчитать количество данного символа в строке (разными способами).
 */
class CharCounter {

    @Test
    void count() {
        assertThat(counter1("Semper Fidelis", 'e')).isEqualTo(3);
        assertThat(counter1("Semper Fidelis", 'a')).isEqualTo(0);
        assertThat(counter1(null, 'e')).isEqualTo(0);
        assertThat(counter1("", 'e')).isEqualTo(0);
    }

    private int counter1(String s, char c) {
        if (s == null) {
            return 0;
        }
        var counter = 0;
        for (var i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                counter++;
            }
        }
        return counter;
    }
}
