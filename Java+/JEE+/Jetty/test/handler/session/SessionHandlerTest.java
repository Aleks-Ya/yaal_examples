package handler.session;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.NullSessionDataStore;
import org.eclipse.jetty.server.session.SessionCache;
import org.eclipse.jetty.server.session.SessionHandler;
import org.junit.Test;

import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SessionHandlerTest {
    private static final int SUCCESS_STATUS = 200;

    @Test
    public void session() throws Exception {
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

        var sessionHandler = createSessionHandler();

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, enContext, ruContext});

        var handlerList = new HandlerList(sessionHandler, contexts);

        var server = new Server(0);
        var sessionIdManager = new DefaultSessionIdManager(server);
        server.setSessionIdManager(sessionIdManager);
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
            var rootUri = URI.create(baseUrl);
            var request = HttpRequest.newBuilder().uri(rootUri).GET().build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode(), equalTo(SUCCESS_STATUS));
            assertThat(response.body(), equalTo("Session: counter=1"));
        }

        {
            var enUri = URI.create(baseUrl + enPath);
            var request = HttpRequest.newBuilder().uri(enUri).GET().build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode(), equalTo(SUCCESS_STATUS));
            assertThat(response.body(), equalTo("Session: counter=2"));
        }

        {
            var ruUri = URI.create(baseUrl + ruPath);
            var request = HttpRequest.newBuilder().uri(ruUri).GET().build();
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            assertThat(response.statusCode(), equalTo(SUCCESS_STATUS));
            assertThat(response.body(), equalTo("Session: counter=3"));
        }

        var rootSessionId = rootHandler.getSession().getId();
        var enSessionId = enHandler.getSession().getId();
        var ruSessionId = ruHandler.getSession().getId();

        assertThat(rootSessionId, equalTo(enSessionId));
        assertThat(rootSessionId, equalTo(ruSessionId));
        server.stop();
    }

    private static SessionHandler createSessionHandler() {
        var sessionHandler = new SessionHandler();
        SessionCache sessionCache = new DefaultSessionCache(sessionHandler);
        var sessionDataStore = new NullSessionDataStore();
        sessionCache.setSessionDataStore(sessionDataStore);
        sessionHandler.setSessionCache(sessionCache);
        sessionHandler.setHttpOnly(true);
        return sessionHandler;
    }
}