package azure.flow.authcode.webapi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

import java.io.IOException;

class BankWebApp implements AutoCloseable {
    private final String authority;
    private final String redirectUriGraph;
    private final String clientId;
    private final Server server;
    private final int port;

    public BankWebApp(int port, String authority, String redirectUriGraph, String clientId) {
        this.port = port;
        this.authority = authority;
        this.redirectUriGraph = redirectUriGraph;
        this.clientId = clientId;
        server = new Server(port);
    }

    @Override
    public void close() throws Exception {
        server.stop();
    }

    public String getBaseUrl() {
        return "http://localhost:" + port;
    }

    static class ShowMessageHandler extends AbstractHandler {
        private final String message;

        ShowMessageHandler(String message) {
            this.message = message;
        }

        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
                throws IOException {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            response.getWriter().printf("<h1>%s</h1>", message);
        }
    }

    public void start() throws Exception {
        var rootContext = new ContextHandler();

        var bankInfoContext = new ContextHandler("/info");
        bankInfoContext.setHandler(new ShowMessageHandler("Info"));

        var redirectContext = new ContextHandler("/redirect");
        redirectContext.setHandler(new RedirectHandler());

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, bankInfoContext, redirectContext});

        var authFilter = new AuthHandler(authority, redirectUriGraph, clientId);
        authFilter.setHandler(contexts);

        var sessionHandler = new SessionHandler();
        var sessionCookieConfig = sessionHandler.getSessionCookieConfig();
        sessionCookieConfig.setPath("/");

        var handlerList = new HandlerList(sessionHandler, authFilter);

        server.setHandler(handlerList);
        server.start();
    }
}
