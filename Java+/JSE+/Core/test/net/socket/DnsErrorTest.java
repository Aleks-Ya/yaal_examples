package net.socket;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Get DNS resolution error.
 */
public class DnsErrorTest {
    @Test(expected = UnknownHostException.class)
    public void unknownHostException() throws IOException {
        Socket socket = new Socket("not.resolved.address.ru", 6666);
        socket.getInputStream();
        socket.close();
    }
}
