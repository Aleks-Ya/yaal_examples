package lang.string.string.substring;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * String#substring(int fromInclusive, int toExclusive)
 * String#substring(int fromInclusive)
 */
class Substring {

    @Test
    void substring() {
        String s = "0123456789";
        assertEquals("12", s.substring(1, 3));
        assertEquals("6789", s.substring(6));
        assertEquals("", s.substring(10));
    }

    @Test
    void substringException1() {
        assertThrows(StringIndexOutOfBoundsException.class, () -> {
            String s = "0";
            assertEquals("", s.substring(1));
            s.substring(2);
        });
    }

    @Test
    void substringException2() {
        assertThrows(StringIndexOutOfBoundsException.class, () -> {
            String s = "012345";
            assertEquals("012345", s.substring(0, 6));
            s.substring(0, 7);
        });
    }
}