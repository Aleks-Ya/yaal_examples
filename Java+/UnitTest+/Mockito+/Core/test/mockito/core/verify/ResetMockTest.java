package mockito.core.verify;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ResetMockTest {

    @Test
    void resetMockStatistics() {
        var mock = mock(Date.class);
        var time = 1L;
        mock.setTime(time);
        verify(mock).setTime(time);
        mock.setTime(time);
        verify(mock, times(2)).setTime(time);
        reset(mock);
        verify(mock, times(0)).setTime(time);
    }
}
