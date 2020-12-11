package server;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;
import util.NetUtil;

import java.io.FileNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;

public class PathDispatcherTest {

    @Test
    public void pathDispatcher() throws InterruptedException {
        var path1 = "/person";
        var body1 = "John";
        var mockResponse1 = new MockResponse().setBody(body1);

        var path2 = "/car";
        var body2 = "BMW";
        var mockResponse2 = new MockResponse().setBody(body2);

        var dispatcher = new PathDispatcher()
                .addPathResponse(path1, mockResponse1)
                .addPathResponse(path2, mockResponse2);

        MockWebServer server = new MockWebServer();
        server.setDispatcher(dispatcher);

        HttpUrl baseUrl1 = server.url(path1);
        HttpUrl baseUrl2 = server.url(path2);

        String actBody1 = NetUtil.urlContentToString(baseUrl1.url());
        assertThat(actBody1, equalTo(body1));

        String actBody2 = NetUtil.urlContentToString(baseUrl2.url());
        assertThat(actBody2, equalTo(body2));

        RecordedRequest request1 = server.takeRequest();
        assertThat(request1.getMethod(), equalTo("GET"));
        assertThat(request1.getPath(), equalTo(path1));

        RecordedRequest request2 = server.takeRequest();
        assertThat(request2.getMethod(), equalTo("GET"));
        assertThat(request2.getPath(), equalTo(path2));
    }

    @Test
    public void pathNotSet() {
        var dispatcher = new PathDispatcher();
        MockWebServer server = new MockWebServer();
        server.setDispatcher(dispatcher);
        HttpUrl url = server.url("/p");
        RuntimeException e = assertThrows(RuntimeException.class, () -> NetUtil.urlContentToString(url.uri()));
        Throwable cause = e.getCause();
        assertThat(cause, is(instanceOf(FileNotFoundException.class)));
        assertThat(cause.getMessage(), endsWith("/p"));
    }

}
