package azure.flow.authcode.web_app_only;

import static azure.flow.authcode.common.Constants.AUTHORITY;
import static azure.flow.authcode.common.Constants.ME_GRAPH_ENDPOINT;
import static azure.flow.authcode.common.Constants.WEB_APP_CLIENT_ID;
import static azure.flow.authcode.common.Constants.WEB_APP_CLIENT_SECRET;
import static azure.flow.authcode.common.Constants.WEB_APP_PORT;
import static azure.flow.authcode.common.Constants.WEB_APP_REDIRECT_URL;

/**
 * "Authentication Code Grant" flow example: Client + WebApp.<br/>
 * Docs: <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-app-types?WT.mc_id=Portal-Microsoft_AAD_RegisteredApps#web-apps">Web apps</a><br/>
 * Azure App Registration: <a href="https://portal.azure.com/#blade/Microsoft_AAD_RegisteredApps/ApplicationMenuBlade/Overview/appId/89f6d017-0ac1-413a-9e05-91ea9c8ada6a/isMSAApp/">msal-authcode-web</a><br/>
 * Run:
 * <ol>
 *      <li>Run {@link MainWebOnly} class</li>
 *      <li>Open <a href="http://localhost:35712/info_web_only"/>http://localhost:35712/info_web_only</a></li>
 * </ol>
 */
public class MainWebOnly {
    public static void main(String[] args) throws Exception {
        var webPath = "/info_web_only";
        try (var webApp = new WebApp(WEB_APP_PORT, AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                WEB_APP_CLIENT_SECRET, ME_GRAPH_ENDPOINT, webPath)) {
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl + webPath);
            Thread.currentThread().join();
        }
    }
}
