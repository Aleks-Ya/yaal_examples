package handler.session;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;
import org.junit.jupiter.api.Test;

import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionHandlerTest {
    private static final int SUCCESS_STATUS = 200;

    @Test
    void session() throws Exception {
        var rootContext = new ContextHandler();
        var rootHandler = new CountSessionHandler();
        rootContext.setHandler(rootHandler);

        var enPath = "/en";
        var enContext = new ContextHandler();
        enContext.setContextPath(enPath);
        var enHandler = new CountSessionHandler();
        enContext.setHandler(enHandler);

        var ruPath = "/ru";
        var ruContext = new ContextHandler(ruPath);
        var ruHandler = new CountSessionHandler();
        ruContext.setHandler(ruHandler);

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, enContext, ruContext});

        var sessionHandler = new SessionHandler();
        var sessionCookieConfig = sessionHandler.getSessionCookieConfig();
        sessionCookieConfig.setPath("/");

        var handlerList = new HandlerList(sessionHandler, contexts);

        var server = new Server(0);
        server.setHandler(handlerList);
        server.start();

        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();
        var baseUrl = "http://localhost:" + port;

        var cookieHandler = new CookieManager();
        var httpClient = HttpClient.newBuilder()
                .cookieHandler(cookieHandler)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        {
            var enUri = URI.create(baseUrl + enPath);
            var request = HttpRequest.newBuilder().uri(enUri).GET().build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode()).isEqualTo(SUCCESS_STATUS);
            assertThat(response.body()).isEqualTo("Session: counter=1");
        }

        {
            var ruUri = URI.create(baseUrl + ruPath);
            var request = HttpRequest.newBuilder().uri(ruUri).GET().build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode()).isEqualTo(SUCCESS_STATUS);
            assertThat(response.body()).isEqualTo("Session: counter=2");
        }

        {
            var rootUri = URI.create(baseUrl);
            var request = HttpRequest.newBuilder().uri(rootUri).GET().build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode()).isEqualTo(SUCCESS_STATUS);
            assertThat(response.body()).isEqualTo("Session: counter=3");
        }

        var rootSessionId = rootHandler.getSession().getId();
        var enSessionId = enHandler.getSession().getId();
        var ruSessionId = ruHandler.getSession().getId();

        assertThat(rootSessionId).isEqualTo(enSessionId);
        assertThat(rootSessionId).isEqualTo(ruSessionId);

        server.stop();
    }
}