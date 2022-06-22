package net.url.connection.http;

import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Вывести статус http-ответа.
 */
class ResponseStatusTest {
    @Test
    void testName() throws Exception {
        var url = new URL("https://www.ya.ru");
        var connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        var responseCode = connection.getResponseCode();
        var contentLength = connection.getContentLength();

        System.out.println(responseCode);
        System.out.println(contentLength);

        assertThat(responseCode).isGreaterThanOrEqualTo(200).isLessThan(300);
        assertThat(contentLength).isGreaterThan(0);
    }
}
