package ok;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetRequest {

    @Test
    void get() throws IOException {
        var expBody = "hello, world!";

        var server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(expBody));
        server.start();

        var url = server.url("/v1/chat/");

        var request = new Request.Builder()
                .url(url)
                .build();

        var client = new OkHttpClient().newBuilder().build();
        var response = client.newCall(request).execute();
        assertThat(response.code(), equalTo(200));
        var actBody = response.body();
        assertNotNull(actBody);
        assertThat(actBody.string(), equalTo(expBody));

        server.shutdown();
    }

}
