package azure.flow.authcode.web_and_api_apps;

import com.microsoft.aad.msal4j.AuthorizationCodeParameters;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.naming.ServiceUnavailableException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.String.format;

class RedirectHandler extends AbstractHandler {
    public static final String REDIRECT_ENDPOINT = "/redirect";
    private final String authority;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUri;

    RedirectHandler(String authority, String clientId, String clientSecret, String redirectUri) {
        this.authority = authority;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        AuthenticationResponse authResponse;
        var url = baseRequest.getHttpURI().toString();
        try {
            authResponse = AuthenticationResponseParser.parse(URI.create(url));
            if (isAuthenticationSuccessful(authResponse)) {
                var oidcResponse = (AuthenticationSuccessResponse) authResponse;
                var authResult = getAuthResultByAuthCode(oidcResponse.getAuthorizationCode(), redirectUri);
                var idToken = authResult.accessToken();
                var nonce = getNonceClaimValueFromIdToken(idToken);
//                validateNonce(request, nonce); TODO enable nonce validation
                var accessToken = authResult.accessToken();
                SessionHelper.setAccessToken(request, accessToken);
            } else {
                var oidcResponse = (AuthenticationErrorResponse) authResponse;
                throw new IOException(format("Request for auth code failed: %s - %s",
                        oidcResponse.getErrorObject().getCode(),
                        oidcResponse.getErrorObject().getDescription()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        var state = SessionHelper.getState(request);
        response.sendRedirect(state.getTargetUrlPath());
    }

    private String getNonceClaimValueFromIdToken(String idToken) throws ParseException {
        return (String) JWTParser.parse(idToken).getHeader().getCustomParam("nonce");
    }

    private void validateNonce(HttpServletRequest request, String actNonce) throws Exception {
        var expNonce = SessionHelper.getState(request).getNonce();
        if (actNonce == null || actNonce.isEmpty() || expNonce == null || !expNonce.equals(actNonce)) {
            throw new Exception(format("Wrong nonce (exp=%s, actual=%s)", expNonce, actNonce));
        }
        System.out.println("Nonce is correct: " + actNonce);
    }

    private static boolean isAuthenticationSuccessful(AuthenticationResponse authResponse) {
        return authResponse instanceof AuthenticationSuccessResponse;
    }

    private IAuthenticationResult getAuthResultByAuthCode(
            AuthorizationCode authorizationCode,
            String currentUri) throws ServiceUnavailableException, MalformedURLException, ExecutionException, InterruptedException {

        IAuthenticationResult result;
        ConfidentialClientApplication app;
        try {
            app = createClientApplication();

            var authCode = authorizationCode.getValue();
            var parameters = AuthorizationCodeParameters.builder(
                    authCode,
                    URI.create(currentUri)).
                    build();

            Future<IAuthenticationResult> future = app.acquireToken(parameters);

            result = future.get();
        } catch (ExecutionException | MalformedURLException | InterruptedException e) {
            throw e;
        }

        if (result == null) {
            throw new ServiceUnavailableException("authentication result was null");
        }
        return result;
    }

    private ConfidentialClientApplication createClientApplication() throws MalformedURLException {
        return ConfidentialClientApplication.builder(clientId, ClientCredentialFactory.createFromSecret(clientSecret)).
                authority(authority).
                build();
    }
}