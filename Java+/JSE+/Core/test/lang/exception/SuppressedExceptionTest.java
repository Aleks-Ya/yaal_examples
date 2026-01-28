package lang.exception;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

class SuppressedExceptionTest {

    @Test
    void suppressedExceptionInTryWithResources() {
        var suppressedException = new IOException("Closing failed");
        assertThatThrownBy(() -> {
            var service = mock(AutoCloseable.class);
            doThrow(suppressedException).when(service).close();
            try (service) {
                throw new IOException("No data");
            }
        })
                .isInstanceOf(IOException.class)
                .hasMessageContaining("No data")
                .hasSuppressedException(suppressedException);
    }

}
