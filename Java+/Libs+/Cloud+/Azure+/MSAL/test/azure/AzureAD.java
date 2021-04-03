package azure;

import org.junit.Test;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Set;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.IClientCredential;
import com.microsoft.aad.msal4j.MsalException;
import com.microsoft.aad.msal4j.SilentParameters;

public class AzureAD {
    private final static String CLIENT_ID = "";
    private final static String AUTHORITY = "https://login.microsoftonline.com/<tenant>/";
    private final static String CLIENT_SECRET = "";
    private final static Set<String> SCOPE = Collections.singleton("https://graph.microsoft.com/.default");
    @Test
    void name() throws MalformedURLException {
        IClientCredential credential = ClientCredentialFactory.createFromSecret(CLIENT_SECRET);

        ConfidentialClientApplication cca =
                ConfidentialClientApplication
                        .builder(CLIENT_ID, credential)
                        .authority(AUTHORITY)
                        .build();


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
