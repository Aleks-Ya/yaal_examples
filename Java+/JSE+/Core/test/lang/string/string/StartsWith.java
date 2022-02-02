package lang.string.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StartsWith {
    @Test
    void test() {
        assertThat("abc".startsWith("abc")).isTrue();
    }
}
