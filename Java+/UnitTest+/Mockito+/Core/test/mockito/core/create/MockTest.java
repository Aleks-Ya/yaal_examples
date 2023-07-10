package mockito.core.create;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MockTest {
    @Test
    void singleRetrunManyInvocations() {
        var mock = mock(File.class);
        long length = 1000L;
        when(mock.length()).thenReturn(length);
        assertThat(mock.length()).isEqualTo(length);
        assertThat(mock.length()).isEqualTo(length);
        assertThat(mock.length()).isEqualTo(length);
    }
}
