package core.net.url.connection.http;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Вывести статус http-ответа.
 */
public class ResponseStatusTest {
    @Test
    public void testName() throws Exception {
        URL url = new URL("https://www.ya.ru");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        int responseCode = connection.getResponseCode();
        int contentLength = connection.getContentLength();

        System.out.println(responseCode);
        System.out.println(contentLength);

        Assert.assertThat(responseCode, Matchers.allOf(Matchers.greaterThanOrEqualTo(200), Matchers.lessThan(300)));
        Assert.assertThat(contentLength, Matchers.greaterThan(0));
    }
}
