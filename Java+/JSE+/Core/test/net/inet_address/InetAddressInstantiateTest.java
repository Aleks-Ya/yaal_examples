package net.inet_address;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InetAddressInstantiateTest {

    @Test
    void isAbsolute() {
        //noinspection ResultOfMethodCallIgnored
        assertThatThrownBy(() -> InetAddress.getByName("http://www.google.com")).isInstanceOf(UnknownHostException.class);
    }
}
