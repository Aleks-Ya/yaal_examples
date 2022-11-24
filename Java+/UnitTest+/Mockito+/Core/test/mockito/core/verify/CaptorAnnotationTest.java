package mockito.core.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Capture arguments that was passed to a mock with {@link Captor}.
 */
class CaptorAnnotationTest {
    @Captor
    ArgumentCaptor<String> captor;
    private AutoCloseable closeable;

    @BeforeEach
    public void open() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void release() throws Exception {
        closeable.close();
    }

    @Test
    void capture() {
        var mock = mock(Service.class, InvocationOnMock::callRealMethod);
        var s1 = mock.upper("abc");
        var s2 = mock.upper("xyz");
        assertThat(s1).isEqualTo("ABC");
        assertThat(s2).isEqualTo("XYZ");
        verify(mock, times(2)).upper(anyString());
        verify(mock).upper("abc");
        verify(mock).upper("xyz");

        verify(mock, times(2)).upper(captor.capture());
        var values = captor.getAllValues();
        assertThat(values).containsExactly("abc", "xyz");
    }

    private static class Service {
        String upper(String s) {
            return s.toUpperCase();
        }
    }
}
