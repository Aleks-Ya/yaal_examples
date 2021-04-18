package net.inet_address;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class InetAddressInstantiateTest {

    @Test
    public void isAbsolute() {
        //noinspection ResultOfMethodCallIgnored
        assertThrows(UnknownHostException.class, () -> InetAddress.getByName("http://www.google.com"));
    }
}
