package azure.claim.web_claim;

import java.io.File;

import static azure.claim.web_claim.Constants.AUTHORITY;
import static azure.claim.web_claim.Constants.ME_GRAPH_ENDPOINT;
import static azure.claim.web_claim.Constants.WEB_APP_CLIENT_CERT_FILE;
import static azure.claim.web_claim.Constants.WEB_APP_CLIENT_CERT_PASSWORD;
import static azure.claim.web_claim.Constants.WEB_APP_CLIENT_ID;
import static azure.claim.web_claim.Constants.WEB_APP_PORT;
import static azure.claim.web_claim.Constants.WEB_APP_REDIRECT_URL;

/**
 * Web-application signed with a certificate.
 * Run:
 * <ol>
 *      <li>{@link MainWebClaim} class</li>
 *      <li>Open <a href="http://localhost:31235/info_claim"/>http://localhost:31235/info_claim</a></li>
 * </ol>
 */
public class MainWebClaim {
    public static void main(String[] args) throws Exception {
        var clientCertFile = new File(MainWebClaim.class.getResource(WEB_APP_CLIENT_CERT_FILE).getFile());
        try (var webApp = new WebApp(WEB_APP_PORT, AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                clientCertFile, WEB_APP_CLIENT_CERT_PASSWORD, ME_GRAPH_ENDPOINT, "/info_claim")) {
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.currentThread().join();
        }
    }
}
