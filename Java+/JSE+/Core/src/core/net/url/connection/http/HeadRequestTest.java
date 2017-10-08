package core.net.url.connection.http;

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

        Assert.assertThat(responseCode, Matchers.allOf(Matchers.greaterThanOrEqualTo(200), Matchers.lessThan(300)));
        Assert.assertThat(contentLength, Matchers.greaterThan(0));
    }
}
