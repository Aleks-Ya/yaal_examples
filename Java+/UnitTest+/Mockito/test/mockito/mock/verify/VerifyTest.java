package mockito.mock.verify;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class VerifyTest {

    @Test
    public void invocationOneTime() {
        Date mock = mock(Date.class);
        long time = 1L;
        mock.setTime(time);
        verify(mock).setTime(time);
    }

    @Test
    public void invocationManyTimes() {
        Date mock = mock(Date.class);
        long time = 1L;
        mock.setTime(time);
        mock.setTime(time);
        verify(mock, times(2)).setTime(time);
    }
}
