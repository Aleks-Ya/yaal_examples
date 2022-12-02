package lang.string.string_builder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Применение метода StringBuilder#insert.
 */
class InsertTest {

    @Test
    void insert() {
        var sb = new StringBuilder("0123");

        // into the middle
        sb.insert(2, 7);
        assertThat(sb).hasToString("01723");

        //into the begin
        sb.insert(0, 5);
        assertThat(sb).hasToString("501723");

        //into the end
        sb.insert(sb.length(), 9);
        assertThat(sb).hasToString("5017239");
    }

    @Test
    void insertString() {
        var sb = new StringBuilder("0123");

        // into the middle
        sb.insert(2, "987");
        assertThat(sb).hasToString("0198723");
    }
}