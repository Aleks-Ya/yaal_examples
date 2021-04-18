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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;

public class GetRequest {

    @Test
    public void get() throws IOException {
        String expBody = "hello, world!";

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(expBody));
        server.start();

        HttpUrl url = server.url("/v1/chat/");

        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Response response = client.newCall(request).execute();
        assertThat(response.code(), equalTo(200));
        ResponseBody actBody = response.body();
        assertNotNull(actBody);
        assertThat(actBody.string(), equalTo(expBody));

        server.shutdown();
    }

}
