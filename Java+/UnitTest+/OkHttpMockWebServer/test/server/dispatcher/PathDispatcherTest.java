package server.dispatcher;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import server.PathDispatcher;
import util.NetUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Return different responses depending on request path.
 */
class PathDispatcherTest {

    @Test
    void pathDispatcher() throws InterruptedException, IOException {
        try (var server = new MockWebServer()) {
            var path1 = "/person";
            var body1 = "John";
            var mockResponse1 = new MockResponse().setBody(body1);

            var path2 = "/car";
            var body2 = "BMW";
            var mockResponse2 = new MockResponse().setBody(body2);

            var dispatcher = new PathDispatcher()
                    .addPathResponse(path1, mockResponse1)
                    .addPathResponse(path2, mockResponse2);

            server.setDispatcher(dispatcher);

            var baseUrl1 = server.url(path1);
            var baseUrl2 = server.url(path2);

            var actBody1 = NetUtil.urlContentToString(baseUrl1.url());
            assertThat(actBody1).isEqualTo(body1);

            var actBody2 = NetUtil.urlContentToString(baseUrl2.url());
            assertThat(actBody2).isEqualTo(body2);

            var request1 = server.takeRequest();
            assertThat(request1.getMethod()).isEqualTo("GET");
            assertThat(request1.getPath()).isEqualTo(path1);

            var request2 = server.takeRequest();
            assertThat(request2.getMethod()).isEqualTo("GET");
            assertThat(request2.getPath()).isEqualTo(path2);
        }
    }

    @Test
    void pathNotSet() throws IOException {
        try (var server = new MockWebServer()) {
            var dispatcher = new PathDispatcher();
            server.setDispatcher(dispatcher);
            var url = server.url("/p");
            assertThatThrownBy(() -> NetUtil.urlContentToString(url.uri()))
                    .isInstanceOf(RuntimeException.class)
                    .cause().isInstanceOf(FileNotFoundException.class).hasMessageEndingWith("/p");
        }
    }

}
