package net.url;

import org.junit.jupiter.api.Test;

import java.net.URL;
import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DnsErrorTest {

    @Test
    void unknownHostException() {
        assertThatThrownBy(() -> {
            var url = new URL("http://not.resolvable.ru/path");
            var conn = url.openConnection();
            conn.connect();
        }).isInstanceOf(UnknownHostException.class);
    }
}
