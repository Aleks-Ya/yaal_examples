package azure.claim.web_claim;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

import java.util.Set;

import static azure.claim.web_claim.Constants.WEB_APP_CALL_API_SCOPE;
import static azure.claim.web_claim.SessionHelper.WEB_APP_ACCESS_TOKEN_ATTR;

class WebApp implements AutoCloseable {
    private final String authority;
    private final String redirectUri;
    private final String webAppClientId;
    private final String clientSecret;
    private final Server server;
    private final int port;
    private final String webPath;

    public WebApp(int port, String authority, String redirectUri, String webAppClientId, String clientSecret, String webPath) {
        this.port = port;
        this.authority = authority;
        this.redirectUri = redirectUri;
        this.webAppClientId = webAppClientId;
        server = new Server(port);
        this.clientSecret = clientSecret;
        this.webPath = webPath;
    }

    @Override
    public void close() throws Exception {
        server.stop();
    }

    public String getBaseUrl() {
        return "http://localhost:" + port;
    }

    public void start() throws Exception {
        var infoContext = new ContextHandler(webPath);
        infoContext.setHandler(new ShowTokenHandler());

        var redirectContext = new ContextHandler("/redirect");
        redirectContext.setHandler(new RedirectHandler(authority, webAppClientId, clientSecret, redirectUri));

        var contexts = new ContextHandlerCollection(infoContext, redirectContext);

        var scopes = Set.of(WEB_APP_CALL_API_SCOPE);
        var authFilter = new AuthFilter(authority, redirectUri, webAppClientId, WEB_APP_ACCESS_TOKEN_ATTR, scopes);
        authFilter.setHandler(contexts);

        var sessionHandler = new SessionHandler();
        sessionHandler.getSessionCookieConfig().setPath("/");

        var handlerList = new HandlerList(sessionHandler, authFilter);

        server.setHandler(handlerList);
        server.start();
    }
}
