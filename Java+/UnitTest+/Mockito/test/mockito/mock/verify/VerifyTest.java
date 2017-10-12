package mockito.mock.verify;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class VerifyTest {

    @Test
    public void invocation() {
        List<String> mock = mock(List.class);
        mock.add("a");
        verify(mock).add("a");
    }
}
