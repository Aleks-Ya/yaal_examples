package azure.claim.web_claim;

import com.microsoft.aad.msal4j.AuthorizationRequestUrlParameters;
import com.microsoft.aad.msal4j.Prompt;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.ResponseMode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static azure.claim.web_claim.RedirectHandler.REDIRECT_ENDPOINT;

class AuthFilter extends HandlerWrapper {
    public static final String GRAPH_USER_READ_SCOPE = "https://graph.microsoft.com/User.Read";
    private static final List<String> NOT_SECURE_PATHS = List.of(REDIRECT_ENDPOINT);
    private final String authority;
    private final String redirectUri;
    private final String clientId;
    private final String tokenAttr;
    private final Set<String> scopes;
    private static final String USER_COUNTRY_CLAIM = "ctry";

    public AuthFilter(String authority, String redirectUri, String clientId, String tokenAttr, Set<String> scopes) {
        this.authority = authority;
        this.redirectUri = redirectUri;
        this.clientId = clientId;
        this.tokenAttr = tokenAttr;
        this.scopes = scopes;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        var path = request.getPathInfo();
        var notSecure = NOT_SECURE_PATHS.stream().anyMatch(path::startsWith);
        if (notSecure) {
            System.out.println("Auth not needed for " + path);
            super.handle(target, baseRequest, request, response);
        } else {
            var session = request.getSession();
            var accessTokenOpt = SessionHelper.getAccessTokenOptional(request, tokenAttr);
            if (accessTokenOpt.isEmpty()) {
                System.out.println("Need authorize session id=" + session.getId());
                authenticate(request, response, tokenAttr, clientId);
            } else {
                System.out.println("Session is already authorized id=" + session.getId());
                super.handle(target, baseRequest, request, response);
            }
        }
    }

    private void authenticate(HttpServletRequest request, HttpServletResponse response, String tokenAttr, String clientId) throws IOException {
        var targetUrlPath = request.getPathInfo();
        var nonce = UUID.randomUUID().toString();
        var stateId = SessionHelper.saveState(request, targetUrlPath, nonce, tokenAttr);
//        var claims = request.getParameter("claims");
        var claims = USER_COUNTRY_CLAIM;
        var authorizationCodeUrl = getAuthorizationCodeUrl(claims, redirectUri, stateId, nonce, clientId);
        response.sendRedirect(authorizationCodeUrl);
    }

    private String getAuthorizationCodeUrl(String claims, String registeredRedirectURL, String state, String nonce, String clientId)
            throws MalformedURLException {
        var pca = PublicClientApplication.builder(clientId).authority(authority).build();
        var parameters = AuthorizationRequestUrlParameters
                .builder(registeredRedirectURL, scopes)
                .responseMode(ResponseMode.QUERY)
                .prompt(Prompt.SELECT_ACCOUNT)
                .state(state)
                .nonce(nonce)
//                .claimsChallenge(claims)
                .build();
        var authorizationUrl = pca.getAuthorizationRequestUrl(parameters).toString();
        System.out.println("Authorization URL: " + authorizationUrl);
        return authorizationUrl;
    }
}