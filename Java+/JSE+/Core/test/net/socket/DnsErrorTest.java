package net.socket;

import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Get DNS resolution error.
 */
class DnsErrorTest {
    @Test
    void unknownHostException() {
        assertThrows(UnknownHostException.class, () -> {
            var socket = new Socket("not.resolved.address.ru", 6666);
            socket.getInputStream();
            socket.close();
        });
    }
}
