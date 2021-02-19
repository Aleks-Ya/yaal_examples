package azure.claim.web_claim;

import static azure.claim.web_claim.Constants.AUTHORITY;
import static azure.claim.web_claim.Constants.WEB_APP_CLIENT_ID;
import static azure.claim.web_claim.Constants.WEB_APP_CLIENT_SECRET;
import static azure.claim.web_claim.Constants.WEB_APP_PORT;
import static azure.claim.web_claim.Constants.WEB_APP_REDIRECT_URL;

/**
 * Web-application displays optional claim "auth_time".
 * Run:
 * <ol>
 *      <li>{@link MainWebClaim} class</li>
 *      <li>Open <a href="http://localhost:31236/info_claim"/>http://localhost:31236/info_claim</a></li>
 * </ol>
 */
public class MainWebClaim {
    public static void main(String[] args) throws Exception {
        try (var webApp = new WebApp(WEB_APP_PORT, AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                WEB_APP_CLIENT_SECRET, "/info_claim")) {
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.currentThread().join();
        }
    }
}
