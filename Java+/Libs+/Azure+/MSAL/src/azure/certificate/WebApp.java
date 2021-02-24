package azure.certificate;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

import java.io.File;
import java.util.Set;

import static azure.certificate.AuthFilter.GRAPH_USER_READ_SCOPE;
import static azure.certificate.SessionHelper.WEB_APP_ACCESS_TOKEN_ATTR;

class WebApp implements AutoCloseable {
    private final String authority;
    private final String redirectUri;
    private final String webAppClientId;
    private final File clientCertFile;
    private final String clientCertPassword;
    private final Server server;
    private final int port;
    private final String meGraphEndpoint;
    private final String webPath;

    public WebApp(int port, String authority, String redirectUri, String webAppClientId, File clientCertFile,
                  String clientCertPassword, String meGraphEndpoint, String webPath) {
        this.port = port;
        this.authority = authority;
        this.redirectUri = redirectUri;
        this.webAppClientId = webAppClientId;
        server = new Server(port);
        this.clientCertFile = clientCertFile;
        this.clientCertPassword = clientCertPassword;
        this.meGraphEndpoint = meGraphEndpoint;
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
        infoContext.setHandler(new InfoHandler("Info Web Only", meGraphEndpoint));

        var redirectContext = new ContextHandler("/redirect");
        redirectContext.setHandler(new RedirectHandler(authority, webAppClientId, clientCertFile, clientCertPassword, redirectUri));

        var contexts = new ContextHandlerCollection(infoContext, redirectContext);

        var scopes = Set.of(GRAPH_USER_READ_SCOPE);
        var authFilter = new AuthFilter(authority, redirectUri, webAppClientId, WEB_APP_ACCESS_TOKEN_ATTR, scopes);
        authFilter.setHandler(contexts);

        var sessionHandler = new SessionHandler();
        sessionHandler.getSessionCookieConfig().setPath("/");

        var handlerList = new HandlerList(sessionHandler, authFilter);

        server.setHandler(handlerList);
        server.start();
    }
}
