package azure.flow.authcode.web_api_graph_apps;

import azure.flow.authcode.common.SessionHelper;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.OnBehalfOfParameters;
import com.microsoft.aad.msal4j.UserAssertion;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static azure.flow.authcode.common.SessionHelper.WEB_APP_ACCESS_TOKEN_ATTR;
import static azure.flow.authcode.web_api_apps.ApiApp.API_APP_SCOPE;

class ApiHandler extends AbstractHandler {
    private final String message;
    private final String webAppClientId;
    private final String webAppClientSecret;
    private final String apiAppAuthority;
    private final String apiAppUrl;

    ApiHandler(String message, String webAppClientId, String webAppClientSecret,
               String apiAppAuthority, String apiAppUrl) {
        this.message = message;
        this.webAppClientId = webAppClientId;
        this.webAppClientSecret = webAppClientSecret;
        this.apiAppAuthority = apiAppAuthority;
        this.apiAppUrl = apiAppUrl;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        var scopes = Set.of(API_APP_SCOPE);
        var userAccessToken = SessionHelper.getAccessTokenOrThrow(request, WEB_APP_ACCESS_TOKEN_ATTR);
        String apiAccessToken = requestOboAccessToken(request, webAppClientId, webAppClientSecret, apiAppAuthority,
                scopes, userAccessToken);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        var me = getDataFromApiApp(apiAccessToken);
        response.getWriter().printf("<h1>%s</h1><p>%s</p>", message, me);
    }

    public static String requestOboAccessToken(HttpServletRequest request, String clientId, String clientSecret,
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

    private String getDataFromApiApp(String accessToken) throws IOException {
        // Microsoft Graph user endpoint
        var url = new URL(apiAppUrl);
        var conn = (HttpURLConnection) url.openConnection();

        // Set the appropriate header fields in the request header.
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Accept", "application/json");

        var response = getResponseStringFromConn(conn);

        var responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException(response);
        }
        return response;
    }

    static String getResponseStringFromConn(HttpURLConnection conn) throws IOException {
        BufferedReader reader;
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        var stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
