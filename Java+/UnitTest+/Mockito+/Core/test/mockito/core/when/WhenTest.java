package mockito.core.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WhenTest {

    @Test
    void returnValue() {
        var mock = mock(Date.class);
        var value = 100L;
        when(mock.getTime()).thenReturn(value);
        assertThat(mock.getTime()).isEqualTo(value);
    }

    @Test
    void voidMethodThrowsException() throws IOException {
        var mock = mock(InputStream.class);
        var expException = new IOException();
        Mockito.doThrow(expException).when(mock).close();
        assertThatThrownBy(mock::close).isEqualTo(expException);
    }

    @Test
    void voidMethodWithParameterThrowsException() {
        var mock = mock(InputStream.class);

        var e1 = new RuntimeException();
        var readlimit1 = 5;
        Mockito.doThrow(e1).when(mock).mark(readlimit1);

        var e2 = new SecurityException();
        var readlimit2 = 10;
        Mockito.doThrow(e2).when(mock).mark(readlimit2);

        assertThatThrownBy(() -> mock.mark(readlimit1)).isEqualTo(e1);
        assertThatThrownBy(() -> mock.mark(readlimit2)).isEqualTo(e2);
        mock.mark(15);
    }

    @Test
    void returnMock() {
        var serverSocketMock = mock(ServerSocket.class);
        var inetAddressMock = mock(InetAddress.class);
        var hostName = "news.com";
        when(inetAddressMock.getHostName()).thenReturn(hostName);
        when(serverSocketMock.getInetAddress()).thenReturn(inetAddressMock);
        assertThat(serverSocketMock.getInetAddress().getHostName()).isEqualTo(hostName);
    }
}
