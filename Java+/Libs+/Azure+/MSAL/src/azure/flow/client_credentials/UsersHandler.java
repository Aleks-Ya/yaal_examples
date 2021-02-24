package azure.flow.client_credentials;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.util.Set;

import static azure.flow.client_credentials.Constants.DEFAULT_GRAPH_SCOPE;
import static azure.flow.client_credentials.HttpClientHelper.getFromUrl;

class UsersHandler extends AbstractHandler {
    private final String header;
    private final String usersGraphEndpoint;
    private final TokenService tokenService;

    UsersHandler(String header, String clientId, String clientSecret, String authority, String usersGraphEndpoint) {
        this.header = header;
        this.usersGraphEndpoint = usersGraphEndpoint;
        var scopes = Set.of(DEFAULT_GRAPH_SCOPE);
        tokenService = new TokenService(clientId, clientSecret, authority, scopes);
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        var accessToken = tokenService.getAccessToken();
        var body = getFromUrl(usersGraphEndpoint, accessToken);
        response.getWriter().printf("<h1>%s</h1><p>%s</p>", header, body);
    }
}
