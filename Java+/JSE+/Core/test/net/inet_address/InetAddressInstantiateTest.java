package net.inet_address;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressInstantiateTest {

    @Test(expected = UnknownHostException.class)
    public void isAbsolute() throws UnknownHostException {
        //noinspection ResultOfMethodCallIgnored
        InetAddress.getByName("http://www.google.com");
    }
}
