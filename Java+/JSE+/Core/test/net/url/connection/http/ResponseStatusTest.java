package net.url.connection.http;

import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;

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

        assertThat(responseCode, allOf(greaterThanOrEqualTo(200), lessThan(300)));
        assertThat(contentLength, greaterThan(0));
    }
}
