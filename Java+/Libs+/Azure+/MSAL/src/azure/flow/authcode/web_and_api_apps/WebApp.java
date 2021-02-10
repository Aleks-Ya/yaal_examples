package azure.flow.authcode.web_and_api_apps;

import azure.flow.authcode.common.AuthHandler;
import azure.flow.authcode.common.RedirectHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

import java.util.Set;

import static azure.flow.authcode.common.AuthHandler.GRAPH_USER_READ_SCOPE;

class WebApp implements AutoCloseable {
    public static final String WEB_APP_SCOPE = "api://msal-web-app-id/Read.ME";
    private final String authority;
    private final String apiAppAuthority;
    private final String apiAppUrl;
    private final String redirectUri;
    private final String webAppClientId;
    private final String webAppClientSecret;
    private final Server server;
    private final int port;
    private final String meGraphEndpoint;

    public WebApp(int port, String authority, String redirectUri, String webAppClientId, String webAppClientSecret,
                  String meGraphEndpoint, String apiAppAuthority, String apiAppUrl) {
        this.port = port;
        this.authority = authority;
        this.redirectUri = redirectUri;
        this.webAppClientId = webAppClientId;
        server = new Server(port);
        this.webAppClientSecret = webAppClientSecret;
        this.meGraphEndpoint = meGraphEndpoint;
        this.apiAppAuthority = apiAppAuthority;
        this.apiAppUrl = apiAppUrl;
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

        var infoWebAndApiContext = new ContextHandler("/info_web_and_api");
        infoWebAndApiContext.setHandler(new ApiHandler("Info Web And Api", meGraphEndpoint, webAppClientId,
                webAppClientSecret, apiAppAuthority, apiAppUrl));

        var redirectContext = new ContextHandler("/redirect");
        redirectContext.setHandler(new RedirectHandler(authority, webAppClientId, webAppClientSecret, redirectUri));

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, infoWebAndApiContext, redirectContext});

        var scopes = Set.of(WEB_APP_SCOPE);
        var authFilter = new AuthHandler(authority, redirectUri, webAppClientId, scopes);
        authFilter.setHandler(contexts);

        var sessionHandler = new SessionHandler();
        var sessionCookieConfig = sessionHandler.getSessionCookieConfig();
        sessionCookieConfig.setPath("/");

        var handlerList = new HandlerList(sessionHandler, authFilter);

        server.setHandler(handlerList);
        server.start();
    }
}
