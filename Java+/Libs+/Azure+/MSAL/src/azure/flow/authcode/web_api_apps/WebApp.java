package azure.flow.authcode.web_api_apps;

import azure.flow.authcode.common.WebAppAuthHandler;
import azure.flow.authcode.common.RedirectHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

import java.util.Set;

import static azure.flow.authcode.common.SessionHelper.WEB_APP_ACCESS_TOKEN_ATTR;

class WebApp implements AutoCloseable {
    public static final String WEB_APP_SCOPE = "api://msal-web-app-id/Read.ME";
    private final String webAppAuthority;
    private final String apiAppAuthority;
    private final String apiAppUrl;
    private final String redirectUri;
    private final String webAppClientId;
    private final String webAppClientSecret;
    private final Server server;
    private final int port;

    public WebApp(int port, String webAppAuthority, String redirectUri, String webAppClientId, String webAppClientSecret,
                  String apiAppAuthority, String apiAppUrl) {
        this.port = port;
        this.webAppAuthority = webAppAuthority;
        this.redirectUri = redirectUri;
        this.webAppClientId = webAppClientId;
        server = new Server(port);
        this.webAppClientSecret = webAppClientSecret;
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
        infoWebAndApiContext.setHandler(new ApiHandler("Info Web And Api", webAppClientId,
                webAppClientSecret, apiAppAuthority, apiAppUrl));

        var redirectContext = new ContextHandler("/redirect");
        redirectContext.setHandler(new RedirectHandler(webAppAuthority, webAppClientId, webAppClientSecret, redirectUri));

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, infoWebAndApiContext, redirectContext});

        var scopes = Set.of(WEB_APP_SCOPE);
        var authFilter = new WebAppAuthHandler(webAppAuthority, redirectUri, webAppClientId, WEB_APP_ACCESS_TOKEN_ATTR, scopes);
        authFilter.setHandler(contexts);

        var sessionHandler = new SessionHandler();
        var sessionCookieConfig = sessionHandler.getSessionCookieConfig();
        sessionCookieConfig.setPath("/");

        var handlerList = new HandlerList(sessionHandler, authFilter);

        server.setHandler(handlerList);
        server.start();
    }
}
