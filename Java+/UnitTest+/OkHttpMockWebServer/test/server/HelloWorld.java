package server;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;
import util.NetUtil;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorld {

    @Test
    public void mock() throws IOException, InterruptedException {
        MockWebServer server = new MockWebServer();

        String body1 = "hello, world!";
        String body2 = "sup, bra?";
        String body3 = "yo dog";
        server.enqueue(new MockResponse().setBody(body1));
        server.enqueue(new MockResponse().setBody(body2));
        server.enqueue(new MockResponse().setBody(body3));

        server.start();

        HttpUrl baseUrl = server.url("/v1/chat/");

        String body = NetUtil.urlContentToString(new URL(baseUrl.url(), "messages/"));
        assertThat(body, equalTo(body1));

        String actBody2 = NetUtil.urlContentToString(new URL(baseUrl.url(), "messages/2"));
        assertThat(actBody2, equalTo(body2));

        String actBody3 = NetUtil.urlContentToString(new URL(baseUrl.url(), "messages/3"));
        assertThat(actBody3, equalTo(body3));

        RecordedRequest request1 = server.takeRequest();
        assertEquals("/v1/chat/messages/", request1.getPath());
        assertThat(request1.getMethod(), equalTo("GET"));

        RecordedRequest request2 = server.takeRequest();
        assertEquals("/v1/chat/messages/2", request2.getPath());

        RecordedRequest request3 = server.takeRequest();
        assertEquals("/v1/chat/messages/3", request3.getPath());

        server.shutdown();
    }
}
