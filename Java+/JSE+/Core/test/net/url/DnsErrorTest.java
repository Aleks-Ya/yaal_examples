package net.url;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DnsErrorTest {

    @Test
    public void unknownHostException() {
        assertThrows(UnknownHostException.class, () -> {
            URL url = new URL("http://not.resolvable.ru/path");
            URLConnection conn = url.openConnection();
            conn.connect();
        });
    }
}
