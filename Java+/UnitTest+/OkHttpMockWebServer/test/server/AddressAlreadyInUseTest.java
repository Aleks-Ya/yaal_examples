package server;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.BindException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressAlreadyInUseTest {

    @Test
    void bindException() {
        assertThatThrownBy(() -> {
            var port = 39031;
            try (var server1 = new MockWebServer()) {
                server1.start(port);
                try (var server2 = new MockWebServer()) {
                    server2.start(port);
                }
            }
        }).isInstanceOf(BindException.class).hasMessage("Address already in use");
    }

    @Test
    void defaultPort() throws IOException {
        try (var server1 = new MockWebServer()) {
            server1.start();
            try (var server2 = new MockWebServer()) {
                server2.start();
            }
        }
    }
}