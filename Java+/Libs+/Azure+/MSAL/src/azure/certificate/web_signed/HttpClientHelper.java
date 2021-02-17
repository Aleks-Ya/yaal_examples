package azure.certificate.web_signed;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.OnBehalfOfParameters;
import com.microsoft.aad.msal4j.UserAssertion;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class HttpClientHelper {
    public static String getFromUrl(String url, String accessToken) throws IOException {
        try {
            var client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build();
            var request = HttpRequest.newBuilder(URI.create(url))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException(String.valueOf(response.statusCode()));
            }
            return response.body();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String requestOboAccessToken(String clientId, String clientSecret,
                                               String authority, Set<String> scopes, String userAccessToken)
            throws java.net.MalformedURLException {
        var clientCredential = ClientCredentialFactory.createFromSecret(clientSecret);
        var app = ConfidentialClientApplication.builder(clientId, clientCredential)
                .authority(authority)
                .build();
        var userAssertion = new UserAssertion(userAccessToken);
        var params = OnBehalfOfParameters.builder(scopes, userAssertion).build();
        IAuthenticationResult result;
        try {
            result = app.acquireToken(params).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return result.accessToken();
    }
}
