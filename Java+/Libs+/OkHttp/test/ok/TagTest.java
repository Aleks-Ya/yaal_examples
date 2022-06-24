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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Transfer objects to Callback by Tags.
 */
class TagTest {
    private static boolean isCallbackVisited = false;

    @Test
    void tag() throws IOException {
        try (var server = new MockWebServer()) {
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
                    assertThat(stringTagValue).isEqualTo(expStringTag);
                    assertThat(dataTagValue).isEqualTo(expDataTag);
                    isCallbackVisited = true;
                }
            });
            while (client.dispatcher().queuedCallsCount() > 0 || client.dispatcher().runningCallsCount() > 0) ;

            assertThat(isCallbackVisited).isTrue();
        }
    }

}
