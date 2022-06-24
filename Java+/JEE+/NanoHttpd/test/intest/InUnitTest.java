package intest;

import fi.iki.elonen.NanoHTTPD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class InUnitTest {
    private static final String CONTENT = "Hello";

    @BeforeEach
    void setup() throws IOException {
        new NanoHTTPD(8080) {
            @Override
            public Response serve(IHTTPSession session) {
                return newFixedLengthResponse(CONTENT);
            }
        }.start(0, true);
    }

    @Test
    void test() throws IOException {
        var is = new URL("http://127.0.0.1:8080").openStream();
        var content = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining());
        assertThat(content).isEqualTo(CONTENT);
    }
}
