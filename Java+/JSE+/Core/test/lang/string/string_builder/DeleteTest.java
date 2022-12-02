package lang.string.string_builder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeleteTest {

    @Test
    void delete() {
        var sb = new StringBuilder("0123");
        sb.delete(1, 2);
        assertThat(sb).hasToString("023");
    }

    @Test
    void deleteAll() {
        var sb = new StringBuilder("0123");
        sb.delete(0, sb.length());
        assertThat(sb.toString().isEmpty()).isTrue();
    }
}