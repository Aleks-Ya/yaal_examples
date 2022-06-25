package lang.string.string_builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteTest {

    @Test
    void delete() {
        var sb = new StringBuilder("0123");
        sb.delete(1, 2);
        assertEquals("023", sb.toString());
    }

    @Test
    void deleteAll() {
        var sb = new StringBuilder("0123");
        sb.delete(0, sb.length());
        assertTrue(sb.toString().isEmpty());
    }
}