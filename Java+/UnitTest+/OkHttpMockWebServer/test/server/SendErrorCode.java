package server;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;
import util.NetUtil;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThrows;

public class SendErrorCode {
    @Test
    public void setResponseCode() throws IOException {
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setResponseCode(418));
        server.start();

        HttpUrl baseUrl = server.url("/v1/chat/");

        RuntimeException e = assertThrows(RuntimeException.class, () -> NetUtil.urlContentToString(baseUrl.uri()));
        Throwable cause = e.getCause();
        assertThat(cause, is(instanceOf(IOException.class)));
        assertThat(cause.getMessage(), startsWith("Server returned HTTP response code: 418 for URL:"));

        server.shutdown();
    }
}
