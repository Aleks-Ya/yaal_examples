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
    private final String clientSecret;
    private final Server server;
    private final int port;
    private final String graphEndpoint;

    public BankWebApp(int port, String authority, String redirectUri, String clientId, String clientSecret, String graphEndpoint) {
        this.port = port;
        this.authority = authority;
        this.redirectUri = redirectUri;
        this.clientId = clientId;
        server = new Server(port);
        this.clientSecret = clientSecret;
        this.graphEndpoint = graphEndpoint;
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
        bankInfoContext.setHandler(new InfoHandler("Info", graphEndpoint));

        var redirectContext = new ContextHandler("/redirect");
        redirectContext.setHandler(new RedirectHandler(authority, clientId, clientSecret, redirectUri));

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
