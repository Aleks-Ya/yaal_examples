package mockito.mock.verify;

import org.junit.Test;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class VerifyTest {

    @Test
    public void invocation() {
        Date mock = mock(Date.class);
        long time = 1L;
        mock.setTime(time);
        verify(mock).setTime(time);
    }
}
