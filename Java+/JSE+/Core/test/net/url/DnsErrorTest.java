package net.url;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DnsErrorTest {

    @Test
    void unknownHostException() {
        assertThrows(UnknownHostException.class, () -> {
            var url = new URL("http://not.resolvable.ru/path");
            var conn = url.openConnection();
            conn.connect();
        });
    }
}
