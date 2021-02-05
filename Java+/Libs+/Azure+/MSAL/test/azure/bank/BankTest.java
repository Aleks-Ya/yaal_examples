package azure.bank;

import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static java.lang.String.format;

public class BankTest {
    private final static String CLIENT_ID = "36922d24-27a7-4845-8978-c1935f155415";
    private final static String TENANT = "26376728-d7c9-4e0b-92b0-979740c8ac58";
    private final static String AUTHORITY = format("https://login.microsoftonline.com/%s/", TENANT);
    private final static String CLIENT_SECRET = "";
    private final static String REDIRECT_URL = "http://localhost:35712/redirect";
    private final static int WEB_APP_PORT = 35712;
    private final static Set<String> SCOPE = Collections.singleton("https://graph.microsoft.com/.default");

    @Test
    public void authenticationCodeFlow() throws Exception {
//        var bankApiApp = new BankApiApp();
//        bankApiApp.start();
        try (var bankWebApp = new BankWebApp(WEB_APP_PORT, AUTHORITY, REDIRECT_URL, CLIENT_ID)) {
            bankWebApp.start();
            var baseUrl = bankWebApp.getBaseUrl();
            System.out.println(baseUrl);

//            var cookieHandler = new CookieManager();
//            var httpClient = HttpClient.newBuilder()
//                    .cookieHandler(cookieHandler)
//                    .followRedirects(HttpClient.Redirect.NORMAL)
//                    .build();
//            var rootUri = URI.create(baseUrl);
//            var request = HttpRequest.newBuilder().uri(rootUri).GET().build();
//            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            Thread.sleep(120000);
        }


//        IClientCredential credential = ClientCredentialFactory.createFromSecret(CLIENT_SECRET);
//
//        ConfidentialClientApplication cca =
//                ConfidentialClientApplication
//                        .builder(CLIENT_ID, credential)
//                        .authority(AUTHORITY)
//                        .build();


    }

//    private static IAuthenticationResult acquireToken() throws Exception {
//
//        // Load token cache from file and initialize token cache aspect. The token cache will have
//        // dummy data, so the acquireTokenSilently call will fail.
//        TokenCacheAspect tokenCacheAspect = new TokenCacheAspect("sample_cache.json");
//
//        IClientCredential credential = ClientCredentialFactory.createFromSecret(CLIENT_SECRET);
//        ConfidentialClientApplication cca =
//                ConfidentialClientApplication
//                        .builder(CLIENT_ID, credential)
//                        .authority(AUTHORITY)
//                        .setTokenCacheAccessAspect(tokenCacheAspect)
//                        .build();
//
//        IAuthenticationResult result;
//        try {
//            SilentParameters silentParameters =
//                    SilentParameters
//                            .builder(SCOPE)
//                            .build();
//
//            // try to acquire token silently. This call will fail since the token cache does not
//            // have a token for the application you are requesting an access token for
//            result = cca.acquireTokenSilently(silentParameters).join();
//        } catch (Exception ex) {
//            if (ex.getCause() instanceof MsalException) {
//
//                ClientCredentialParameters parameters =
//                        ClientCredentialParameters
//                                .builder(SCOPE)
//                                .build();
//
//                // Try to acquire a token. If successful, you should see
//                // the token information printed out to console
//                result = cca.acquireToken(parameters).join();
//            } else {
//                // Handle other exceptions accordingly
//                throw ex;
//            }
//        }
//        return result;
//    }
}
