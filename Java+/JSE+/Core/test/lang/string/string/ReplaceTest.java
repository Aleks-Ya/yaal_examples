package lang.string.string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReplaceTest {
    @Test
    void replaceAllNoRegex() {
        assertThat("ababab".replace("a", "")).isEqualTo("bbb");
    }
}
