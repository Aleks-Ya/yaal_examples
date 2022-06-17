package net.url.connection.http;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(contentLength).isGreaterThan(0);
        var bis = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        var content = bis.lines().collect(Collectors.joining("\n"));
        conn.disconnect();
        assertThat((double) content.length()).isCloseTo(contentLength, Offset.offset(1000D));
        assertThat(content).containsSubsequence("<html");

        assertThat(responseCode).isGreaterThanOrEqualTo(200).isLessThan(300);
    }
}
