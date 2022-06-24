package ok;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AsyncRequestsTest {

    @Test
    void asyncMultiRequests() throws IOException {
        var expBody1 = "Body #1";
        var expBody2 = "Body #2";

        try (var server = new MockWebServer()) {
            server.enqueue(new MockResponse().setBody(expBody1));
            server.enqueue(new MockResponse().setBody(expBody2));
            server.start();

            var url = server.url("/v1/chat/");
            var request1 = new Request.Builder().url(url).build();
            var request2 = new Request.Builder().url(url).build();

            var client = new OkHttpClient().newBuilder().build();

            var callback = new InfoCallback();
            client.newCall(request1).enqueue(callback);
            client.newCall(request2).enqueue(callback);
            while (client.dispatcher().queuedCallsCount() > 0 || client.dispatcher().runningCallsCount() > 0) ;

            assertThat(callback.bodyList).containsExactlyInAnyOrder(expBody1, expBody2);
            assertThat(callback.exceptionList).isEmpty();
        }
    }

    private static class InfoCallback implements Callback {
        public final List<String> bodyList = new ArrayList<>();
        public final List<Exception> exceptionList = new ArrayList<>();

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            exceptionList.add(e);
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            bodyList.add(response.body().string());
        }

    }

}
