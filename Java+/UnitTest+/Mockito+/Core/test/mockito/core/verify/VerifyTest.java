package mockito.core.verify;

import mockito.core.FakeInterface;
import org.junit.jupiter.api.Test;
import org.mockito.exceptions.verification.NoInteractionsWanted;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class VerifyTest {

    @Test
    void invocationOneTime() {
        var mock = mock(Date.class);
        var time = 1L;
        mock.setTime(time);
        verify(mock).setTime(time);
    }

    @Test
    void invocationDifferentMethods() {
        var mock = mock(FakeInterface.class);
        mock.method1();
        mock.method2();
        mock.method3("a");
        mock.method3("b");
        verify(mock).method3("b");
        verify(mock).method3("a");
        verify(mock).method2();
        verify(mock).method1();
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

    @Test
    void verifyNoMoreInteractions_success() {
        var mock = mock(File.class);

        assertThat(mock.canRead()).isFalse();
        assertThat(mock.setExecutable(true)).isFalse();
        verify(mock).canRead();
        verify(mock).setExecutable(true);
        verifyNoMoreInteractions(mock);

        assertThat(mock.delete()).isFalse();
        verify(mock).delete();
        verifyNoMoreInteractions(mock);
        verify(mock).canRead();
        verify(mock).canRead();
    }

    @Test
    void verifyNoMoreInteractions_fail() {
        var mock = mock(File.class);
        assertThat(mock.canRead()).isFalse();
        assertThat(mock.delete()).isFalse();
        verify(mock).canRead();
        assertThatThrownBy(() -> verifyNoMoreInteractions(mock)).isInstanceOf(NoInteractionsWanted.class);
    }
}
