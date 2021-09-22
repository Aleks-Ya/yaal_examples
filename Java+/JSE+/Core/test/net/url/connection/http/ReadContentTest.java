package net.url.connection.http;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;

/**
 * Read content from HttpURLConnection.
 */
class ReadContentTest {
    @Test
    void read() throws IOException {
        var url = new URL("https://www.ya.ru");
        var conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        var responseCode = conn.getResponseCode();
        var contentLength = conn.getContentLength();
        assertThat(contentLength, greaterThan(0));
        var bis = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        var content = bis.lines().collect(Collectors.joining("\n"));
        conn.disconnect();
        assertThat((double) content.length(), closeTo(contentLength, 1000D));
        assertThat(content, containsString("</html>"));

        assertThat(responseCode, allOf(greaterThanOrEqualTo(200), lessThan(300)));
    }
}
