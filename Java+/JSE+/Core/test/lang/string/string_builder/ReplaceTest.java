package lang.string.string_builder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReplaceTest {

    @Test
    void replace() {
        var sb = new StringBuilder("0123456");
        sb.replace(1, 2, "abcde");
        assertThat(sb).hasToString("0abcde23456");
    }
}