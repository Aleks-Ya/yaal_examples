package azure.flow.authcode.web_api_graph_apps;

import azure.flow.authcode.common.RedirectHandler;
import azure.flow.authcode.common.AuthFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

import java.util.Set;

import static azure.flow.authcode.common.Constants.WEB_APP_PERMISSION;
import static azure.flow.authcode.common.SessionHelper.WEB_APP_ACCESS_TOKEN_ATTR;

class WebApp implements AutoCloseable {
    private final String webAppAuthority;
    private final String apiAppAuthority;
    private final String apiAppUrl;
    private final String redirectUri;
    private final String webAppClientId;
    private final String webAppClientSecret;
    private final Server server;
    private final int port;
    private final String webPath;

    public WebApp(int port, String webAppAuthority, String redirectUri, String webAppClientId, String webAppClientSecret,
                  String apiAppAuthority, String apiAppUrl, String webPath) {
        this.port = port;
        this.webAppAuthority = webAppAuthority;
        this.redirectUri = redirectUri;
        this.webAppClientId = webAppClientId;
        server = new Server(port);
        this.webAppClientSecret = webAppClientSecret;
        this.apiAppAuthority = apiAppAuthority;
        this.apiAppUrl = apiAppUrl;
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
        infoContext.setHandler(new ApiHandler("Info Web Api Graph", webAppClientId,
                webAppClientSecret, apiAppAuthority, apiAppUrl));

        var redirectContext = new ContextHandler("/redirect");
        redirectContext.setHandler(new RedirectHandler(webAppAuthority, webAppClientId, webAppClientSecret, redirectUri));

        var contexts = new ContextHandlerCollection(infoContext, redirectContext);

        var scopes = Set.of(WEB_APP_PERMISSION);
        var authFilter = new AuthFilter(webAppAuthority, redirectUri, webAppClientId, WEB_APP_ACCESS_TOKEN_ATTR, scopes);
        authFilter.setHandler(contexts);

        var sessionHandler = new SessionHandler();
        sessionHandler.getSessionCookieConfig().setPath("/");

        var handlerList = new HandlerList(sessionHandler, authFilter);

        server.setHandler(handlerList);
        server.start();
    }
}
