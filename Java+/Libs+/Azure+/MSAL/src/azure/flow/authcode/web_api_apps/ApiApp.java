package azure.flow.authcode.web_api_apps;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.session.SessionHandler;

class ApiApp implements AutoCloseable {
    public static final String API_APP_SCOPE = "api://msal-api-app-id/Read.ME";
    private final Server server;
    private final String apiPath;
    private final int apiAppPort;

    public ApiApp(int apiAppPort, String apiPath) {
        this.apiAppPort = apiAppPort;
        server = new Server(apiAppPort);
        this.apiPath = apiPath;
    }

    @Override
    public void close() throws Exception {
        server.stop();
    }

    public String getBaseUrl() {
        return "http://localhost:" + apiAppPort;
    }

    public void start() throws Exception {
        var infoContext = new ContextHandler(apiPath);
        infoContext.setHandler(new InfoHandler("Info ME"));

        var sessionHandler = new SessionHandler();
        sessionHandler.getSessionCookieConfig().setPath("/");

        var handlerList = new HandlerList(sessionHandler, infoContext);

        server.setHandler(handlerList);
        server.start();
    }
}
