package mockito.mock.stub;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StubTest {

    @Test
    void thenReturn() {
        var mock = mock(Iterator.class);
        when(mock.hasNext()).thenReturn(true);
        assertThat(mock.hasNext()).isTrue();
    }

    @Test
    void then() {
        var mock = mock(Source.class);
        when(mock.doWork(anyString())).then(invocation -> {
            String src = invocation.getArgument(0);
            return src + src;
        });
        assertThat(mock.doWork("ab")).isEqualTo("abab");
    }

    @Test
    void doAnswerTest() {
        var mock = mock(Source.class);
        doAnswer(invocation -> {
            String src = invocation.getArgument(0);
            return src + src;
        }).when(mock).doWork("ab");
        assertThat(mock.doWork("ab")).isEqualTo("abab");
    }

    private interface Source {
        String doWork(String src);
    }
}
