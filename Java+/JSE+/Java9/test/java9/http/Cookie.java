package java9.http;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.CookieManager;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

public class Cookie {
    private static final String SET_COOKIE_HEADER = "Set-Cookie";
    private static final String COOKIE_HEADER = "Cookie";
    private static final int SUCCESS_STATUS = 200;

    @Test
    public void setCookiesByServer() throws IOException, InterruptedException {
        var cookieName1 = "abc";
        var cookieValue1 = "1";
        var cookiePair1 = cookieName1 + "=" + cookieValue1;

        var cookieName2 = "efg";
        var cookieValue2 = "2";
        var cookiePair2 = cookieName2 + "=" + cookieValue2;

        var body1 = "body1";
        var body2 = "body2";

        var server = new MockWebServer();
        server.enqueue(new MockResponse()
                .addHeader(SET_COOKIE_HEADER, cookiePair1)
                .addHeader(SET_COOKIE_HEADER, cookiePair2)
                .setBody(body1));
        server.enqueue(new MockResponse().setBody(body2));
        server.start();
        var baseUrl = server.url("/").uri();

        var cookieHandler = new CookieManager();
        var httpClient = HttpClient.newBuilder().cookieHandler(cookieHandler).build();
        {
            var request = HttpRequest.newBuilder().uri(baseUrl).GET().build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode(), equalTo(SUCCESS_STATUS));
            assertThat(response.body(), equalTo(body1));

            var handlerCookies = cookieHandler.getCookieStore().getCookies();
            assertThat(handlerCookies.toString(), equalTo("[abc=1, efg=2]"));

            var responseSetCookieHeader = response.headers().allValues(SET_COOKIE_HEADER);
            assertThat(responseSetCookieHeader.toString(), equalTo("[abc=1, efg=2]"));

            var requestCookieHeader = server.takeRequest().getHeader(COOKIE_HEADER);
            assertThat(requestCookieHeader, nullValue());
        }
        {
            var request = HttpRequest.newBuilder().uri(baseUrl).GET().build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode(), equalTo(SUCCESS_STATUS));
            assertThat(response.body(), equalTo(body2));

            var handlerCookies = cookieHandler.getCookieStore().getCookies();
            assertThat(handlerCookies.toString(), equalTo("[abc=1, efg=2]"));

            var responseSetCookieHeader = response.headers().allValues(SET_COOKIE_HEADER);
            assertThat(responseSetCookieHeader, emptyIterable());

            var requestCookieHeader = server.takeRequest().getHeader(COOKIE_HEADER);
            assertThat(requestCookieHeader, equalTo("abc=1; efg=2"));
        }
        server.shutdown();
    }

    @Test
    public void setCookiesByClient() throws IOException, InterruptedException {
        var cookieName1 = "abc";
        var cookieValue1 = "1";
        var cookiePair1 = cookieName1 + "=" + cookieValue1;
        var body1 = "body1";

        var server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(body1));
        server.start();
        var baseUrl = server.url("/").uri();

        var httpClient = HttpClient.newBuilder().build();
        var request = HttpRequest.newBuilder()
                .uri(baseUrl)
                .header("Cookie", cookiePair1)
                .GET()
                .build();
        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode(), equalTo(SUCCESS_STATUS));
        assertThat(response.body(), equalTo(body1));

        var request1 = server.takeRequest();
        var headers = request1.getHeaders();
        var actCookies = headers.get("Cookie");
        server.shutdown();
        assertThat(actCookies, equalTo(cookiePair1));
    }

}

