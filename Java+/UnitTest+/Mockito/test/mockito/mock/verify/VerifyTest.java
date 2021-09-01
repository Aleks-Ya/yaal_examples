package mockito.mock.verify;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VerifyTest {

    @Test
    void invocationOneTime() {
        var mock = mock(Date.class);
        var time = 1L;
        mock.setTime(time);
        verify(mock).setTime(time);
    }

    @Test
    void invocationManyTimes() {
        var mock = mock(Date.class);
        var time = 1L;
        mock.setTime(time);
        mock.setTime(time);
        verify(mock, times(2)).setTime(time);
    }

    @Test
    void methodChain() {
        var fileMock = mock(File.class);
        var expAbsolutePath = "abc.txt";
        when(fileMock.getAbsolutePath()).thenReturn(expAbsolutePath);

        var pathMock = mock(Path.class);
        when(pathMock.toFile()).thenReturn(fileMock);

        var file = pathMock.toFile();
        var actAbsolutePath = file.getAbsolutePath();
        assertThat(actAbsolutePath).isEqualTo(expAbsolutePath);

        verify(pathMock).toFile();
        verify(file).getAbsolutePath();
    }
}
