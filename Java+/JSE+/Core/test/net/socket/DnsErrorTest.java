package net.socket;

import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Get DNS resolution error.
 */
class DnsErrorTest {
    @Test
    void unknownHostException() {
        assertThatThrownBy(() -> {
            var socket = new Socket("not.resolved.address.ru", 6666);
            socket.getInputStream();
            socket.close();
        }).isInstanceOf(UnknownHostException.class);
    }
}
