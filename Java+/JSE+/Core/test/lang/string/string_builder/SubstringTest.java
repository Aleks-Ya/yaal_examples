package lang.string.string_builder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubstringTest {

    @Test
    void substring() {
        var sb = new StringBuilder("0123");
        assertThat(sb.substring(1, 2)).isEqualTo("1");
    }
}