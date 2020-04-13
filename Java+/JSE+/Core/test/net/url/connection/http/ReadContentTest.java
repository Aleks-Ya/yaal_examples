package net.url.connection.http;

import org.junit.Test;

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
public class ReadContentTest {
    @Test
    public void read() throws IOException {
        URL url = new URL("https://www.ya.ru");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        int responseCode = conn.getResponseCode();
        int contentLength = conn.getContentLength();
        assertThat(contentLength, greaterThan(0));
        BufferedReader bis = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String content = bis.lines().collect(Collectors.joining("\n"));
        conn.disconnect();
        assertThat((double) content.length(), closeTo((double) contentLength, 1000D));
        assertThat(content, containsString("</html>"));

        assertThat(responseCode, allOf(greaterThanOrEqualTo(200), lessThan(300)));
    }
}
