package format.number;

import org.junit.Test;

import java.text.NumberFormat;

import static org.junit.Assert.assertEquals;

/**
 * @author yablokov a.
 */
public class NumberFormatUsage {
    @Test
    public void integer() throws Exception {
        NumberFormat format = NumberFormat.getIntegerInstance();
        String str = format.format(100);
        assertEquals("100", str);
    }
}
