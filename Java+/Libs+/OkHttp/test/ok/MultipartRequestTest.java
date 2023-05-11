package ok;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class MultipartRequestTest {

    @Test
    void multipart() throws IOException {
        var expBody = "hello, world!";

        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse().setBody(expBody));
            server.start();

            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("title", "Mr")
                    .addFormDataPart("name", "John")
                    .build();

            var url = server.url("/v1/chat/");
            var request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .build();

            var client = new OkHttpClient().newBuilder().build();
            try (var response = client.newCall(request).execute()) {
                assertThat(response.code()).isEqualTo(200);
                var actBody = response.body();
                assertThat(actBody).isNotNull();
                assertThat(actBody.string()).isEqualTo(expBody);
            }
        }
    }

}
