package mockito.mock.stub;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StubTest {

    @Test
    public void thenReturn() {
        Iterator mock = mock(Iterator.class);
        when(mock.hasNext()).thenReturn(true);
        assertTrue(mock.hasNext());
    }

    @Test
    public void then() {
        Source mock = mock(Source.class);
        when(mock.doWork(anyString())).then(invocation -> {
            String src = invocation.getArgument(0);
            return src + src;
        });
        assertThat(mock.doWork("ab"), equalTo("abab"));
    }

    @Test
    public void doAnswerTest() {
        Source mock = mock(Source.class);
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
