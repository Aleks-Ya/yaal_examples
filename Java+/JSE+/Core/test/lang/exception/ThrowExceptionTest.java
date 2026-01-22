package lang.exception;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ThrowExceptionTest {

    @Test
    void throwException() {
        assertThatThrownBy(() -> {
            throw new IOException("No data");
        })
                .isInstanceOf(IOException.class)
                .hasMessageContaining("No");
    }

}
