package azure.flow.authcode.web_api_graph_apps;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

class ApiApp implements AutoCloseable {
    public static final String API_APP_SCOPE = "api://msal-api-app-id/Read.ME";
    private final String tokenAuthority;
    private final String apiAppClientId;
    private final String apiAppClientSecret;
    private final Server server;
    private final int apiAppPort;
    private final String meGraphEndpoint;

    public ApiApp(int apiAppPort, String tokenAuthority, String apiAppClientId, String apiAppClientSecret,
                  String meGraphEndpoint) {
        this.apiAppPort = apiAppPort;
        this.tokenAuthority = tokenAuthority;
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
        var infoContext = new ContextHandler("/me");
        infoContext.setHandler(new OboInfoHandler("Info ME", meGraphEndpoint, tokenAuthority, apiAppClientId,
                apiAppClientSecret));

        var sessionHandler = new SessionHandler();
        sessionHandler.getSessionCookieConfig().setPath("/");

        var handlerList = new HandlerList(sessionHandler, infoContext);

        server.setHandler(handlerList);
        server.start();
    }
}
