package server.junit.rule;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class ClientAndServerTest {

    private static final int PORT = 56699;
    private static ClientAndServer mockServer;

    @BeforeAll
    public static void beforeClass() {
        mockServer = startClientAndServer(PORT);
    }

    @AfterAll
    public static void afterClass() {
        mockServer.stop();
    }

    @Test
    void test() throws IOException {
        final var PATH = "/login";
        final var CONTENT_TYPE = "application/json; charset=utf-8";

        mockServer.when(request().withMethod("GET").withPath(PATH), exactly(1))
                .respond(response().withStatusCode(401).withHeaders(new Header("Content-Type", CONTENT_TYPE))
                        .withBody("{ message: 'incorrect username and password combination' }"));

        var url = new URL("http://localhost:" + PORT + PATH);
        var connection = url.openConnection();
        assertThat(connection.getContentType()).isEqualTo(CONTENT_TYPE);
    }

}
