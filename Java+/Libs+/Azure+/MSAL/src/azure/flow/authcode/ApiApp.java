package azure.flow.authcode;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

class ApiApp implements AutoCloseable {
    public static final String API_APP_SCOPE = "api://msal-api-app-id/Read.ME";
    private final String authority;
    private final String apiAppClientId;
    private final String apiAppClientSecret;
    private final Server server;
    private final int apiAppPort;
    private final String meGraphEndpoint;

    public ApiApp(int apiAppPort, String authority, String apiAppClientId, String apiAppClientSecret,
                  String meGraphEndpoint) {
        this.apiAppPort = apiAppPort;
        this.authority = authority;
        this.apiAppClientId = apiAppClientId;
        server = new Server(apiAppPort);
        this.apiAppClientSecret = apiAppClientSecret;
        this.meGraphEndpoint = meGraphEndpoint;
    }

    @Override
    public void close() throws Exception {
        server.stop();
    }

    public String getBaseUrl() {
        return "http://localhost:" + apiAppPort;
    }

    public void start() throws Exception {
        var rootContext = new ContextHandler();

        var infoContext = new ContextHandler("/me");
        infoContext.setHandler(new InfoHandler("Info ME", meGraphEndpoint));

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, infoContext});

        var sessionHandler = new SessionHandler();
        var sessionCookieConfig = sessionHandler.getSessionCookieConfig();
        sessionCookieConfig.setPath("/");

        var handlerList = new HandlerList(sessionHandler, contexts);

        server.setHandler(handlerList);
        server.start();
    }
}
