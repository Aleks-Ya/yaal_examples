package server;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import util.NetUtil;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorld {

    @Test
    void mock() throws IOException, InterruptedException {
        var server = new MockWebServer();

        var body1 = "hello, world!";
        var body2 = "sup, bra?";
        var body3 = "yo dog";
        server.enqueue(new MockResponse().setBody(body1));
        server.enqueue(new MockResponse().setBody(body2));
        server.enqueue(new MockResponse().setBody(body3));

        server.start();

        var baseUrl = server.url("/v1/chat/");

        var body = NetUtil.urlContentToString(new URL(baseUrl.url(), "messages/"));
        assertThat(body, equalTo(body1));

        var actBody2 = NetUtil.urlContentToString(new URL(baseUrl.url(), "messages/2"));
        assertThat(actBody2, equalTo(body2));

        var actBody3 = NetUtil.urlContentToString(new URL(baseUrl.url(), "messages/3"));
        assertThat(actBody3, equalTo(body3));

        var request1 = server.takeRequest();
        assertEquals("/v1/chat/messages/", request1.getPath());
        assertThat(request1.getMethod(), equalTo("GET"));

        var request2 = server.takeRequest();
        assertEquals("/v1/chat/messages/2", request2.getPath());

        var request3 = server.takeRequest();
        assertEquals("/v1/chat/messages/3", request3.getPath());

        server.shutdown();
    }
}
