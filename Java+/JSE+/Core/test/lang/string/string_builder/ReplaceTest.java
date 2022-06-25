package lang.string.string_builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReplaceTest {

    @Test
    void replace() {
        var sb = new StringBuilder("0123456");
        sb.replace(1, 2, "abcde");
        assertEquals("0abcde23456", sb.toString());
    }
}