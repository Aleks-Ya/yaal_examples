package format.number;

import org.junit.Test;

import java.text.NumberFormat;

import static org.junit.Assert.assertEquals;

public class NumberFormatTest {
    @Test
    public void test() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        String str = format.format(100);
        assertEquals("100", str);
    }
}
