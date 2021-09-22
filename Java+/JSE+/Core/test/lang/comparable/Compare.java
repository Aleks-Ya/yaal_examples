package lang.comparable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Compare {

    @Test
    void numbers() {
        var i = Integer.valueOf(100);
        assertEquals(0, i.compareTo(100));
        assertEquals(1, i.compareTo(99));
        assertEquals(-1, i.compareTo(101));
    }
}
