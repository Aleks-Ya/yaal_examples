package intest;

import fi.iki.elonen.NanoHTTPD;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class InUnitTest {
    private static final String CONTENT = "Hello";

    @Before
    public void setup() throws IOException {
        new NanoHTTPD(8080) {
            @Override
            public Response serve(IHTTPSession session) {
                return newFixedLengthResponse(CONTENT);
            }
        }.start(0, true);
    }

    @Test
    public void test() throws IOException {
        InputStream is = new URL("http://127.0.0.1:8080").openStream();
        String content = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining());
        assertThat(content, equalTo(CONTENT));
    }
}
