package azure.claim.web_claim;

import static azure.claim.web_claim.Constants.AUTHORITY;
import static azure.claim.web_claim.Constants.WEB_APP_CLIENT_ID;
import static azure.claim.web_claim.Constants.WEB_APP_CLIENT_SECRET;
import static azure.claim.web_claim.Constants.WEB_APP_PORT;
import static azure.claim.web_claim.Constants.WEB_APP_REDIRECT_URL;
import static azure.claim.web_claim.Constants.WEB_PATH;

/**
 * Web-application displays optional claim "auth_time".<br/>
 * Azure App Registration: <a href="https://portal.azure.com/#blade/Microsoft_AAD_RegisteredApps/ApplicationMenuBlade/Overview/appId/22325e20-04f1-4977-bf33-cba197d0668f/isMSAApp/">msal-optional-claim</a><br/>
 * Run:
 * <ol>
 *      <li>Run {@link MainOptionalClaim} class</li>
 *      <li>Open <a href="http://localhost:31236/info_claim"/>http://localhost:31236/info_claim</a></li>
 * </ol>
 */
public class MainOptionalClaim {
    public static void main(String[] args) throws Exception {
        try (var webApp = new WebApp(WEB_APP_PORT, AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                WEB_APP_CLIENT_SECRET, WEB_PATH)) {
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl + WEB_PATH);
            Thread.currentThread().join();
        }
    }
}
