package lang.string.string_builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Применение метода StringBuilder#insert.
 */
class InsertTest {

    @Test
    void insert() {
        var sb = new StringBuilder("0123");

        // into the middle
        sb.insert(2, 7);
        assertEquals("01723", sb.toString());

        //into the begin
        sb.insert(0, 5);
        assertEquals("501723", sb.toString());

        //into the end
        sb.insert(sb.length(), 9);
        assertEquals("5017239", sb.toString());
    }

    @Test
    void insertString() {
        var sb = new StringBuilder("0123");

        // into the middle
        sb.insert(2, "987");
        assertEquals("0198723", sb.toString());
    }
}