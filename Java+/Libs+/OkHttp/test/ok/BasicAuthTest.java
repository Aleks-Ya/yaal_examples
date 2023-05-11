package ok;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class BasicAuthTest {

    @Test
    void header() throws IOException {
        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse());
            server.start();

            var url = server.url("/v1/chat/");
            var credential = Credentials.basic("john", "pass");
            var request = new Request.Builder()
                    .url(url)
                    .header("Authorization", credential)
                    .build();

            var client = new OkHttpClient().newBuilder().build();
            try (var response = client.newCall(request).execute()) {
                assertThat(response.code()).isEqualTo(200);
            }

        }
    }

    // NOT WORK
    @Test
    void authenticator() throws IOException {
        var expBody = "hello, world!";

        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse().setBody(expBody));
            server.start();

            var url = server.url("/v1/chat/");

            var request = new Request.Builder()
                    .url(url)
                    .build();

            var client = new OkHttpClient().newBuilder()
                    .authenticator((route, response) -> {
                        var credential = Credentials.basic("scott", "tiger");
                        return response.request().newBuilder().header("Authorization", credential).build();
                    })
                    .build();
            try (var response = client.newCall(request).execute()) {
                assertThat(response.code()).isEqualTo(200);
                var actBody = response.body();
                assertThat(actBody).isNotNull();
                assertThat(actBody.string()).isEqualTo(expBody);
            }
        }
    }

}
