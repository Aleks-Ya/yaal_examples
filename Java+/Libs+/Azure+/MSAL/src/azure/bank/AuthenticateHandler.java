package azure.bank;

import com.microsoft.aad.msal4j.AuthorizationRequestUrlParameters;
import com.microsoft.aad.msal4j.Prompt;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.ResponseMode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.UUID;

public class AuthenticateHandler extends AbstractHandler {
    private final String authority;
    private final String redirectUri;
    private final String clientId;
    private static final String USER_COUNTRY_CLAIM = "ctry";

    AuthenticateHandler(String authority, String redirectUri, String clientId) {
        this.authority = authority;
        this.redirectUri = redirectUri;
        this.clientId = clientId;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        var state = UUID.randomUUID().toString();
        var nonce = UUID.randomUUID().toString();
//        var claims = request.getParameter("claims");
        var claims = USER_COUNTRY_CLAIM;
        var authorizationCodeUrl = getAuthorizationCodeUrl(
                claims,
                "User.Read",
                redirectUri,
                state,
                nonce);
        response.sendRedirect(authorizationCodeUrl);
    }

    private String getAuthorizationCodeUrl(String claims, String scope, String registeredRedirectURL, String state, String nonce)
            throws MalformedURLException {

        var updatedScopes = scope == null ? "" : scope;

        var pca = PublicClientApplication.builder(clientId).authority(authority).build();

        var parameters = AuthorizationRequestUrlParameters
                .builder(registeredRedirectURL, Collections.singleton(updatedScopes))
                .responseMode(ResponseMode.QUERY)
                .prompt(Prompt.SELECT_ACCOUNT)
                .state(state)
                .nonce(nonce)
//                .claimsChallenge(claims)
                .build();

        return pca.getAuthorizationRequestUrl(parameters).toString();
    }
}