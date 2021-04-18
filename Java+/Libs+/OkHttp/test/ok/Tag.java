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
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Transfer objects to Callback by Tags.
 */
public class Tag {
    private static boolean isCallbackVisited = false;

    @Test
    public void tag() throws IOException {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse());
        server.start();

        String expStringTag = "the_tag";
        LocalDate expDataTag = LocalDate.of(2020, 10, 25);
        HttpUrl url = server.url("/");
        Request request = new Request.Builder()
                .url(url)
                .tag(String.class, expStringTag)
                .tag(LocalDate.class, expDataTag)
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                String stringTagValue = call.request().tag(String.class);
                LocalDate dataTagValue = call.request().tag(LocalDate.class);
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
