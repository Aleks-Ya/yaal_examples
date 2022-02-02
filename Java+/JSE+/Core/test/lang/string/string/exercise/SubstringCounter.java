package lang.string.string.exercise;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Подсчитать количество подстроки в строку (разными способами).
 */
class SubstringCounter {

    /**
     * Алгоритм на основе String#indexOf.
     */
    @Test
    void count1() {
        assertThat(counter1("doremefadosolliasido", "do")).isEqualTo(3);
        assertThat(counter1("Semper Fidelis", "not")).isEqualTo(0);
        assertThat(counter1("Semper Fidelis", "")).isEqualTo(0);
        assertThat(counter1(null, "no")).isEqualTo(0);
        assertThat(counter1("", "a")).isEqualTo(0);
        assertThat(counter1("Semper Fidelis", null)).isEqualTo(0);
    }

    private int counter1(String src, String substring) {
        if (src == null || substring == null || substring.isEmpty()) {
            return 0;
        }
        var counter = 0;
        var index = 0;
        while ((index = src.indexOf(substring, index)) != -1) {
            counter++;
            index++;
        }
        return counter;
    }

    /**
     * Алгоритм на основе String#startsWith.
     * todo Переписать на основе параметризованных тестов.
     */
    @Test
    void count2() {
        assertThat(counter2("doremefadosolliasido", "do")).isEqualTo(3);
        assertThat(counter2("Semper Fidelis", "not")).isEqualTo(0);
        assertThat(counter2("Semper Fidelis", "")).isEqualTo(0);
        assertThat(counter2(null, "no")).isEqualTo(0);
        assertThat(counter2("", "a")).isEqualTo(0);
        assertThat(counter2("Semper Fidelis", null)).isEqualTo(0);
    }

    private int counter2(String src, String substring) {
        if (src == null || substring == null || substring.isEmpty()) {
            return 0;
        }
        var counter = 0;
        var index = 0;
        while (index < src.length()) {
            if (src.startsWith(substring, index)) {
                counter++;
                index += substring.length();
            } else {
                index++;
            }
        }
        return counter;
    }
}
