package util.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class RussianTest {
    private static final String TEXT = "Каждый охотник желает знать, где сидит фазан, ёЁ and in English";

    @Test
    void dot() {
        assertThat(Pattern.matches("^.*$", TEXT)).isTrue();
        assertThat(Pattern.matches("^[,\\s\\wЁёА-я]*$", TEXT)).isTrue();
    }
}