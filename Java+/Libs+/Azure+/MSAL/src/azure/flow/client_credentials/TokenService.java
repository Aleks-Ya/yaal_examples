package azure.flow.client_credentials;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;

import java.util.Set;

class TokenService {
    private final String clientId;
    private final String clientSecret;
    private final String authority;
    private final Set<String> scopes;
    private String accessToken;

    TokenService(String clientId, String clientSecret, String authority, Set<String> scopes) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authority = authority;
        this.scopes = scopes;
    }

    public String getAccessToken() {
        if (accessToken == null) {
            accessToken = requestAccessToken();
        }
        return accessToken;
    }

    private String requestAccessToken() {
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
