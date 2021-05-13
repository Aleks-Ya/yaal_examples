package server;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import util.NetUtil;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SendErrorCode {
    @Test
    public void setResponseCode() throws IOException {
        var server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(418));
        server.start();

        var baseUrl = server.url("/v1/chat/");

        var e = assertThrows(RuntimeException.class, () -> NetUtil.urlContentToString(baseUrl.uri()));
        var cause = e.getCause();
        assertThat(cause, is(instanceOf(IOException.class)));
        assertThat(cause.getMessage(), startsWith("Server returned HTTP response code: 418 for URL:"));

        server.shutdown();
    }
}
