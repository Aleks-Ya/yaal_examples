package mockito.core.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Capture arguments that was passed to a mock with {@link Captor}.
 */
class CaptorAnnotationGenericsTest {
    @Captor
    private ArgumentCaptor<List<String>> captor;
    private AutoCloseable closeable;

    @BeforeEach
    void open() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void release() throws Exception {
        closeable.close();
    }

    @Test
    void capture() {
        var mock = mock(Service.class, InvocationOnMock::callRealMethod);
        var s1 = mock.upper(List.of("abc"));
        var s2 = mock.upper(List.of("xyz"));
        assertThat(s1).containsOnly("ABC");
        assertThat(s2).containsOnly("XYZ");
        verify(mock, times(2)).upper(anyList());
        verify(mock).upper(List.of("abc"));
        verify(mock).upper(List.of("xyz"));

        verify(mock, times(2)).upper(captor.capture());
        var values = captor.getAllValues();
        assertThat(values).hasSize(2);
        assertThat(values.get(0)).containsExactly("abc");
        assertThat(values.get(1)).containsExactly("xyz");
    }

    private static class Service {
        List<String> upper(List<String> strings) {
            return strings.stream().map(String::toUpperCase).toList();
        }
    }
}
