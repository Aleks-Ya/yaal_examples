package azure.flow.client_credentials;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

class WebApp implements AutoCloseable {
    private final String authority;
    private final String clientId;
    private final String clientSecret;
    private final Server server;
    private final int port;
    private final String usersGraphEndpoint;
    private final String webPath;

    public WebApp(int port, String authority, String clientId, String clientSecret,
                  String usersGraphEndpoint, String webPath) {
        this.port = port;
        this.authority = authority;
        this.clientId = clientId;
        server = new Server(port);
        this.clientSecret = clientSecret;
        this.usersGraphEndpoint = usersGraphEndpoint;
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
        var userContext = new ContextHandler(webPath);
        userContext.setHandler(new UsersHandler("Graph Users", clientId, clientSecret, authority, usersGraphEndpoint));
        server.setHandler(userContext);
        server.start();
    }
}
