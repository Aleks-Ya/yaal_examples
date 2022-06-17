package net.url.connection.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Send HEAD HTTP request.
 */
class HeadRequestTest {
    @Test
    void sendHeadRequest() throws IOException {
        var url = new URL("https://www.ya.ru");
        var conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("HEAD");
        conn.connect();
        var responseCode = conn.getResponseCode();
        var contentLength = conn.getContentLength();
        var headerMap = conn.getHeaderFields();
        conn.disconnect();

        System.out.println(responseCode);
        System.out.println(contentLength);
        System.out.println(headerMap);

        assertThat(responseCode).isGreaterThanOrEqualTo(200).isLessThan(300);
        assertThat(contentLength).isGreaterThan(0);
    }
}
