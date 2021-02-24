package azure.flow.client_credentials;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
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
    private final String clientId;
    private final String clientSecret;
    private final String authority;
    private final String usersGraphEndpoint;
    private String accessToken;

    UsersHandler(String header, String clientId, String clientSecret, String authority, String usersGraphEndpoint) {
        this.header = header;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authority = authority;
        this.usersGraphEndpoint = usersGraphEndpoint;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        if (accessToken == null) {
            var scopes = Set.of(DEFAULT_GRAPH_SCOPE);
            accessToken = requestAccessToken(clientId, clientSecret, authority, scopes);
        }
        var body = getFromUrl(usersGraphEndpoint, accessToken);
        response.getWriter().printf("<h1>%s</h1><p>%s</p>", header, body);
    }

    public static String requestAccessToken(String clientId, String clientSecret,
                                            String authority, Set<String> scopes) {
        try {
            var cert = ClientCredentialFactory.createFromSecret(clientSecret);
            var app = ConfidentialClientApplication.builder(clientId, cert)
                    .authority(authority)
                    .build();
            var params = ClientCredentialParameters.builder(scopes).build();
            var result = app.acquireToken(params).join();
            return result.accessToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
