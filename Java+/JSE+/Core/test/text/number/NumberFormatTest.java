package text.number;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;

import static org.assertj.core.api.Assertions.assertThat;

class NumberFormatTest {
    @Test
    void test() {
        var format = NumberFormat.getIntegerInstance();
        var str = format.format(100);
        assertThat(str).isEqualTo("100");
    }
}
