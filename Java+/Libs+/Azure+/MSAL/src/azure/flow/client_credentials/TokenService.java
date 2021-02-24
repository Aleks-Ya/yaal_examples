package azure.flow.client_credentials;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.ITokenCacheAccessAspect;
import com.microsoft.aad.msal4j.MsalException;
import com.microsoft.aad.msal4j.SilentParameters;

import java.util.Set;

class TokenService {
    private final String clientId;
    private final String clientSecret;
    private final String authority;
    private final Set<String> scopes;
    private final ITokenCacheAccessAspect tokenCacheAccessAspect = new InMemoryTokenCacheAspect();

    TokenService(String clientId, String clientSecret, String authority, Set<String> scopes) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authority = authority;
        this.scopes = scopes;
    }

    public String getAccessToken() {
        try {
            var cert = ClientCredentialFactory.createFromSecret(clientSecret);
            var app = ConfidentialClientApplication.builder(clientId, cert)
                    .authority(authority)
                    .setTokenCacheAccessAspect(tokenCacheAccessAspect)
                    .build();
            IAuthenticationResult result;
            try {
                var params = SilentParameters.builder(scopes).build();
                result = app.acquireTokenSilently(params).join();
                System.out.println("Got access token silently");
            } catch (Exception e) {
                if (e.getCause() instanceof MsalException) {
                    var params = ClientCredentialParameters.builder(scopes).build();
                    result = app.acquireToken(params).join();
                    System.out.println("Got access token from auth server");
                } else {
                    throw e;
                }
            }
            return result.accessToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
