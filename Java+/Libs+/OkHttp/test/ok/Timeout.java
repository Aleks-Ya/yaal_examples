package ok;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Timeout {

    @Test
    public void timeoutException() throws IOException {
        int timeoutSec = 2;

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse()
                .throttleBody(5, timeoutSec * 2, TimeUnit.SECONDS)
                .setBody("hello, world!"));
        server.start();

        HttpUrl url = server.url("/");
        Request request = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .callTimeout(Duration.ofSeconds(timeoutSec))
                .build();
        Response response = client.newCall(request).execute();
        InterruptedIOException e = assertThrows(InterruptedIOException.class, () -> {
            ResponseBody body = response.body();
            assertThat(body, notNullValue());
            body.string();
        });
        assertThat(e.getMessage(), equalTo("timeout"));
        server.shutdown();
    }

    @Test
    public void noTimeout() throws IOException {
        int timeoutSec = 2;

        MockWebServer server = new MockWebServer();
        String body = "hello, world!";
        long bytesPerPeriod = 5;
        server.enqueue(new MockResponse()
                .throttleBody(bytesPerPeriod, timeoutSec / 2, TimeUnit.SECONDS)
                .setBody(body));
        server.start();

        HttpUrl url = server.url("/");

        Request request = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(Duration.ofSeconds(timeoutSec))
                .build();
        Response response = client.newCall(request).execute();
        assertThat(response.code(), equalTo(200));
        ResponseBody actBody = response.body();
        assertNotNull(actBody);
        assertThat(actBody.string(), equalTo(body));

        server.shutdown();
    }

}
