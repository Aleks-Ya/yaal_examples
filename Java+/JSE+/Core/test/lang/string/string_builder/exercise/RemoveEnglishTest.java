package lang.string.string_builder.exercise;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Задача: из исходной строки удалить буквы английского алфавита.
 */
class RemoveEnglishTest {
    @Test
    public void test() {
        final StringBuilder sb = new StringBuilder("Всем all привет");
        final String engAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int i = 0;
        while (i < sb.length()) {
            char c = sb.charAt(i);
            if (engAlphabet.indexOf(c) != -1) {
                sb.deleteCharAt(i);
            } else {
                i++;
            }
        }
        assertThat(sb).hasToString("Всем  привет");
    }
}
