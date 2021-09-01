package mockito.mock.stub;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StubTest {

    @Test
    void thenReturn() {
        var mock = mock(Iterator.class);
        when(mock.hasNext()).thenReturn(true);
        assertTrue(mock.hasNext());
    }

    @Test
    void then() {
        var mock = mock(Source.class);
        when(mock.doWork(anyString())).then(invocation -> {
            String src = invocation.getArgument(0);
            return src + src;
        });
        assertThat(mock.doWork("ab"), equalTo("abab"));
    }

    @Test
    void doAnswerTest() {
        var mock = mock(Source.class);
        doAnswer(invocation -> {
            String src = invocation.getArgument(0);
            return src + src;
        }).when(mock).doWork("ab");
        assertThat(mock.doWork("ab"), equalTo("abab"));
    }

    private interface Source {
        String doWork(String src);
    }
}
