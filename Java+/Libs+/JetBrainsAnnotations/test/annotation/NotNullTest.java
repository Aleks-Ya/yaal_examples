package annotation;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NotNullTest {
    @Test
    @SuppressWarnings("unused")
    public void nullValue() {
        assertThatThrownBy(() -> {
            String s = null;
            var res = toUpperCase(s);
        }).isInstanceOf(NullPointerException.class);
    }

    private String toUpperCase(@NotNull("Cannot process null") String str) {
        return str.toUpperCase();
    }
}
