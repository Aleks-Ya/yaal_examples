package net.url.connection;

import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Send HEAD HTTP request.
 */
public class HeadRequestTest {
    @Test
    public void sendHeadRequest() throws IOException {

        URL url = new URL("https://www.ya.ru");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("HEAD");
        conn.connect();
        int responseCode = conn.getResponseCode();
        int contentLength = conn.getContentLength();
        Map<String, List<String>> headerMap = conn.getHeaderFields();
        conn.disconnect();

        System.out.println(responseCode);
        System.out.println(contentLength);
        System.out.println(headerMap);

        assertThat(responseCode, allOf(greaterThanOrEqualTo(200), lessThan(300)));
        assertThat(contentLength, greaterThan(0));
    }
}
