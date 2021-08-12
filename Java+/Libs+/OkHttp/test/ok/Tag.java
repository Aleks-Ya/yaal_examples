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
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Transfer objects to Callback by Tags.
 */
class Tag {
    private static boolean isCallbackVisited = false;

    @Test
    void tag() throws IOException {
        var server = new MockWebServer();
        server.enqueue(new MockResponse());
        server.start();

        var expStringTag = "the_tag";
        var expDataTag = LocalDate.of(2020, 10, 25);
        var url = server.url("/");
        var request = new Request.Builder()
                .url(url)
                .tag(String.class, expStringTag)
                .tag(LocalDate.class, expDataTag)
                .build();

        var client = new OkHttpClient().newBuilder().build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                var stringTagValue = call.request().tag(String.class);
                var dataTagValue = call.request().tag(LocalDate.class);
                assertThat(stringTagValue, equalTo(expStringTag));
                assertThat(dataTagValue, equalTo(expDataTag));
                isCallbackVisited = true;
            }
        });
        while (client.dispatcher().queuedCallsCount() > 0 || client.dispatcher().runningCallsCount() > 0) ;

        assertTrue(isCallbackVisited);
        server.shutdown();
    }

}
