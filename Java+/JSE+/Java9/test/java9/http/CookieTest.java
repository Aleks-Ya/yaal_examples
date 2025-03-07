package java9.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.CookieManager;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

class CookieTest {
    private static final String SET_COOKIE_HEADER = "Set-Cookie";
    private static final String COOKIE_HEADER = "Cookie";
    private static final int SUCCESS_STATUS = 200;

    @Test
    void setCookiesByServer() throws IOException, InterruptedException {
        var cookieName1 = "abc";
        var cookieValue1 = "1";
        var cookiePair1 = cookieName1 + "=" + cookieValue1;

        var cookieName2 = "efg";
        var cookieValue2 = "2";
        var cookiePair2 = cookieName2 + "=" + cookieValue2;

        var body1 = "body1";
        var body2 = "body2";

        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse()
                    .addHeader(SET_COOKIE_HEADER, cookiePair1)
                    .addHeader(SET_COOKIE_HEADER, cookiePair2)
                    .setBody(body1));
            server.enqueue(new MockResponse().setBody(body2));
            server.start();
            var baseUrl = server.url("/").uri();

            var cookieHandler = new CookieManager();
            var builder = HttpClient.newBuilder().cookieHandler(cookieHandler);
            try (var httpClient = builder.build()) {
                {
                    var request = HttpRequest.newBuilder().uri(baseUrl).GET().build();
                    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    assertThat(response.statusCode()).isEqualTo(SUCCESS_STATUS);
                    assertThat(response.body()).isEqualTo(body1);

                    var handlerCookies = cookieHandler.getCookieStore().getCookies();
                    assertThat(handlerCookies).hasToString("[abc=1, efg=2]");

                    var responseSetCookieHeader = response.headers().allValues(SET_COOKIE_HEADER);
                    assertThat(responseSetCookieHeader).hasToString("[abc=1, efg=2]");

                    var requestCookieHeader = server.takeRequest().getHeader(COOKIE_HEADER);
                    assertThat(requestCookieHeader).isNull();
                }
                {
                    var request = HttpRequest.newBuilder().uri(baseUrl).GET().build();
                    var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    assertThat(response.statusCode()).isEqualTo(SUCCESS_STATUS);
                    assertThat(response.body()).isEqualTo(body2);

                    var handlerCookies = cookieHandler.getCookieStore().getCookies();
                    assertThat(handlerCookies).hasToString("[abc=1, efg=2]");

                    var responseSetCookieHeader = response.headers().allValues(SET_COOKIE_HEADER);
                    assertThat(responseSetCookieHeader).isEmpty();

                    var requestCookieHeader = server.takeRequest().getHeader(COOKIE_HEADER);
                    assertThat(requestCookieHeader).isEqualTo("abc=1; efg=2");
                }
            }
        }
    }

    @Test
    void setCookiesByClient() throws IOException, InterruptedException {
        var cookieName1 = "abc";
        var cookieValue1 = "1";
        var cookiePair1 = cookieName1 + "=" + cookieValue1;
        var body1 = "body1";

        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse().setBody(body1));
            server.start();
            var baseUrl = server.url("/").uri();

            try (var client = HttpClient.newBuilder().build()) {
                var request = HttpRequest.newBuilder()
                        .uri(baseUrl)
                        .header("Cookie", cookiePair1)
                        .GET()
                        .build();
                var response = client.send(request, HttpResponse.BodyHandlers.ofString());
                assertThat(response.statusCode()).isEqualTo(SUCCESS_STATUS);
                assertThat(response.body()).isEqualTo(body1);

                var request1 = server.takeRequest();
                var headers = request1.getHeaders();
                var actCookies = headers.get("Cookie");
                assertThat(actCookies).isEqualTo(cookiePair1);
            }
        }
    }

}

