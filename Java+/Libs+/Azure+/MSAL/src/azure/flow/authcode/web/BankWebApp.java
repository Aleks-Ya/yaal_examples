package azure.flow.authcode.web;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

class BankWebApp implements AutoCloseable {
    private final String authority;
    private final String redirectUri;
    private final String clientId;
    private final Server server;
    private final int port;

    public BankWebApp(int port, String authority, String redirectUri, String clientId) {
        this.port = port;
        this.authority = authority;
        this.redirectUri = redirectUri;
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

    public void start() throws Exception {
        var rootContext = new ContextHandler();

        var bankInfoContext = new ContextHandler("/info");
        bankInfoContext.setHandler(new InfoHandler("Info"));

        var redirectContext = new ContextHandler("/redirect");
        redirectContext.setHandler(new RedirectHandler());

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, bankInfoContext, redirectContext});

        var authFilter = new AuthHandler(authority, redirectUri, clientId);
        authFilter.setHandler(contexts);

        var sessionHandler = new SessionHandler();
        var sessionCookieConfig = sessionHandler.getSessionCookieConfig();
        sessionCookieConfig.setPath("/");

        var handlerList = new HandlerList(sessionHandler, authFilter);

        server.setHandler(handlerList);
        server.start();
    }
}
