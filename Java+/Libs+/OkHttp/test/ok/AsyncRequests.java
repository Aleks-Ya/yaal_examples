package ok;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;

public class AsyncRequests {

    @Test
    public void asyncMultiRequests() throws IOException {
        String expBody1 = "Body #1";
        String expBody2 = "Body #2";

        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody(expBody1));
        server.enqueue(new MockResponse().setBody(expBody2));
        server.start();

        HttpUrl url = server.url("/v1/chat/");
        Request request1 = new Request.Builder().url(url).build();
        Request request2 = new Request.Builder().url(url).build();

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        InfoCallback callback = new InfoCallback();
        client.newCall(request1).enqueue(callback);
        client.newCall(request2).enqueue(callback);
        while (client.dispatcher().queuedCallsCount() > 0 || client.dispatcher().runningCallsCount() > 0) ;

        assertThat(callback.bodyList, containsInAnyOrder(expBody1, expBody2));
        assertThat(callback.exceptionList, empty());

        server.shutdown();
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