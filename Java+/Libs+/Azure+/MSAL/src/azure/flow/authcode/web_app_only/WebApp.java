package azure.flow.authcode.web_app_only;

import azure.flow.authcode.common.RedirectHandler;
import azure.flow.authcode.common.WebAppAuthHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

import java.util.Set;

import static azure.flow.authcode.common.SessionHelper.WEB_APP_ACCESS_TOKEN_ATTR;
import static azure.flow.authcode.common.WebAppAuthHandler.GRAPH_USER_READ_SCOPE;

class WebApp implements AutoCloseable {
    public static final String WEB_APP_SCOPE = "api://msal-web-app-id/Read.ME";
    private final String authority;
    private final String redirectUri;
    private final String webAppClientId;
    private final String webAppClientSecret;
    private final Server server;
    private final int port;
    private final String meGraphEndpoint;

    public WebApp(int port, String authority, String redirectUri, String webAppClientId, String webAppClientSecret,
                  String meGraphEndpoint) {
        this.port = port;
        this.authority = authority;
        this.redirectUri = redirectUri;
        this.webAppClientId = webAppClientId;
        server = new Server(port);
        this.webAppClientSecret = webAppClientSecret;
        this.meGraphEndpoint = meGraphEndpoint;
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

        var infoWebOnlyContext = new ContextHandler("/info_web_only");
        infoWebOnlyContext.setHandler(new InfoHandler("Info Web Only", meGraphEndpoint));

        var redirectContext = new ContextHandler("/redirect");
        redirectContext.setHandler(new RedirectHandler(authority, webAppClientId, webAppClientSecret, redirectUri));

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, infoWebOnlyContext, redirectContext});

        var scopes = Set.of(GRAPH_USER_READ_SCOPE);
        var authFilter = new WebAppAuthHandler(authority, redirectUri, webAppClientId, WEB_APP_ACCESS_TOKEN_ATTR, scopes);
        authFilter.setHandler(contexts);

        var sessionHandler = new SessionHandler();
        var sessionCookieConfig = sessionHandler.getSessionCookieConfig();
        sessionCookieConfig.setPath("/");

        var handlerList = new HandlerList(sessionHandler, authFilter);

        server.setHandler(handlerList);
        server.start();
    }
}
