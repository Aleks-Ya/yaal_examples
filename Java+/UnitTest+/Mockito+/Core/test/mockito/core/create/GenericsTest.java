package mockito.core.create;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Create mock for Generic classes.
 */
class GenericsTest {
    @Test
    void string() {
        List<String> mock = mock();
        assertThat(mock.size()).isEqualTo(0);
    }
}
