package lang.string.string.charat;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CharAt {

    @Test
    public void substring() {
        String s = "0123";
        assertEquals('1', s.charAt(1));
    }

    @Test
    public void substringException1() {
        //noinspection ResultOfMethodCallIgnored
        assertThrows(IllegalArgumentException.class, () -> "0".charAt(1));
    }
}