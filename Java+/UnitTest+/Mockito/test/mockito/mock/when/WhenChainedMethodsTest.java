package mockito.mock.when;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.ServerSocket;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WhenChainedMethodsTest {

    @Test
    void manuallyChained() {
        var serverSocketMock = mock(ServerSocket.class);
        var inetAddressMock = mock(InetAddress.class);
        var hostName = "news.com";
        when(inetAddressMock.getHostName()).thenReturn(hostName);
        when(serverSocketMock.getInetAddress()).thenReturn(inetAddressMock);
        assertThat(serverSocketMock.getInetAddress().getHostName()).isEqualTo(hostName);
    }

    @Test
    void deepStubChained() {
        var serverSocketMock = mock(ServerSocket.class, RETURNS_DEEP_STUBS);
        var hostName = "news.com";
        when(serverSocketMock.getInetAddress().getHostName()).thenReturn(hostName);
        assertThat(serverSocketMock.getInetAddress().getHostName()).isEqualTo(hostName);
    }
}
