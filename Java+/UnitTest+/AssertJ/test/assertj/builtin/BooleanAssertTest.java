package assertj.builtin;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BooleanAssertTest {
    @Test
    void primitive() {
        assertThat(true).isTrue();
        assertThat(false).isFalse();
    }

    @Test
    void wrapper() {
        assertThat(Boolean.TRUE).isTrue();
        assertThat(Boolean.FALSE).isFalse();
    }
}
