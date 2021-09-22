package text.number;

import org.junit.jupiter.api.Test;

import java.text.NumberFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberFormatTest {
    @Test
    void test() {
        var format = NumberFormat.getIntegerInstance();
        var str = format.format(100);
        assertEquals("100", str);
    }
}
