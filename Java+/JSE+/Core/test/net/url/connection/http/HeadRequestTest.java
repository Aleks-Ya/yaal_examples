package net.url.connection.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;

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

        assertThat(responseCode, allOf(greaterThanOrEqualTo(200), lessThan(300)));
        assertThat(contentLength, greaterThan(0));
    }
}
