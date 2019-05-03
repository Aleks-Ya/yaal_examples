package net.url;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class DnsErrorTest {

    @Test(expected = UnknownHostException.class)
    public void unknownHostException() throws IOException {
        URL url = new URL("http://not.resolvable.ru/path");
        URLConnection conn = url.openConnection();
        conn.connect();
    }
}
