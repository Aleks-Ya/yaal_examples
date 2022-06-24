package ok;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TimeoutTest {

    @Test
    void timeoutException() throws IOException {
        var timeoutSec = 2;

        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse()
                    .throttleBody(5, timeoutSec * 2, TimeUnit.SECONDS)
                    .setBody("hello, world!"));
            server.start();

            var url = server.url("/");
            var request = new Request.Builder().url(url).build();

            var client = new OkHttpClient().newBuilder()
                    .callTimeout(Duration.ofSeconds(timeoutSec))
                    .build();
            var response = client.newCall(request).execute();
            var e = assertThrows(InterruptedIOException.class, () -> {
                var body = response.body();
                assertThat(body).isNotNull();
                body.string();
            });
            assertThat(e.getMessage()).isEqualTo("timeout");
        }
    }

    @Test
    void noTimeout() throws IOException {
        var timeoutSec = 2;

        try (var server = new MockWebServer()) {
            var body = "hello, world!";
            long bytesPerPeriod = 5;
            server.enqueue(new MockResponse()
                    .throttleBody(bytesPerPeriod, timeoutSec / 2, TimeUnit.SECONDS)
                    .setBody(body));
            server.start();

            var url = server.url("/");

            var request = new Request.Builder().url(url).build();

            var client = new OkHttpClient().newBuilder()
                    .readTimeout(Duration.ofSeconds(timeoutSec))
                    .build();
            var response = client.newCall(request).execute();
            assertThat(response.code()).isEqualTo(200);
            var actBody = response.body();
            assertThat(actBody).isNotNull();
            assertThat(actBody.string()).isEqualTo(body);

        }
    }

}
