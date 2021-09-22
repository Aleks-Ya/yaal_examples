package lang.string.string.charat;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CharAt {

    @Test
    void substring() {
        String s = "0123";
        assertEquals('1', s.charAt(1));
    }

    @Test
    void substringException1() {
        //noinspection ResultOfMethodCallIgnored
        assertThrows(IllegalArgumentException.class, () -> "0".charAt(1));
    }
}