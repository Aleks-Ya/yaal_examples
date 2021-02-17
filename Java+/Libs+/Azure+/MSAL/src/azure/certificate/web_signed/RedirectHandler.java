package azure.certificate.web_signed;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.String.format;

public class RedirectHandler extends AbstractHandler {
    public static final String REDIRECT_ENDPOINT = "/redirect";
    private final String authority;
    private final String clientId;
    private final File clientCertFile;
    private final String clientCertPassword;
    private final String redirectUri;

    public RedirectHandler(String authority, String clientId, File clientCertFile, String clientCertPassword, String redirectUri) {
        this.authority = authority;
        this.clientId = clientId;
        this.clientCertFile = clientCertFile;
        this.clientCertPassword = clientCertPassword;
        this.redirectUri = redirectUri;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        var state = SessionHelper.getState(request);
        var tokenAttr = state.getTokenAttr();
        System.out.println("TokenAttr: " + tokenAttr);
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
                SessionHelper.setAccessToken(request, tokenAttr, accessToken);
            } else {
                var oidcResponse = (AuthenticationErrorResponse) authResponse;
                throw new IOException(format("Request for auth code failed: %s - %s",
                        oidcResponse.getErrorObject().getCode(),
                        oidcResponse.getErrorObject().getDescription()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    private IAuthenticationResult getAuthResultByAuthCode(AuthorizationCode authorizationCode, String currentUri)
            throws ServiceUnavailableException, IOException, ExecutionException, InterruptedException,
            CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException {
        IAuthenticationResult result;
        ConfidentialClientApplication app;
        app = createClientApplication();
        var authCode = authorizationCode.getValue();
        var parameters = AuthorizationCodeParameters.builder(
                authCode,
                URI.create(currentUri)).
                build();
        Future<IAuthenticationResult> future = app.acquireToken(parameters);
        result = future.get();
        if (result == null) {
            throw new ServiceUnavailableException("authentication result was null");
        }
        return result;
    }

    private ConfidentialClientApplication createClientApplication()
            throws IOException, CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException {
        var cert = ClientCredentialFactory.createFromCertificate(new FileInputStream(clientCertFile), clientCertPassword);
        return ConfidentialClientApplication.builder(clientId, cert)
                .authority(authority)
                .build();
    }
}