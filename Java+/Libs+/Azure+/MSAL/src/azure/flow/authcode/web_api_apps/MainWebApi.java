package azure.flow.authcode.web_api_apps;

import static azure.flow.authcode.common.Constants.API_APP_PORT;
import static azure.flow.authcode.common.Constants.AUTHORITY;
import static azure.flow.authcode.common.Constants.TOKEN_AUTHORITY;
import static azure.flow.authcode.common.Constants.WEB_APP_CLIENT_ID;
import static azure.flow.authcode.common.Constants.WEB_APP_CLIENT_SECRET;
import static azure.flow.authcode.common.Constants.WEB_APP_PORT;
import static azure.flow.authcode.common.Constants.WEB_APP_REDIRECT_URL;

/**
 * "Authentication Code Grant" flow example: Client + WebApp + ApiApp.<br/>
 * Docs: <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-app-types?WT.mc_id=Portal-Microsoft_AAD_RegisteredApps#web-apps">Web apps</a><br/>
 * Azure App Registrations: <a href="https://portal.azure.com/#blade/Microsoft_AAD_RegisteredApps/ApplicationMenuBlade/Overview/appId/89f6d017-0ac1-413a-9e05-91ea9c8ada6a/isMSAApp/">msal-authcode-web</a>,
 * <a href="https://portal.azure.com/#blade/Microsoft_AAD_RegisteredApps/ApplicationMenuBlade/Overview/appId/e95e7874-3073-4c5e-851c-84c68317cbd7/isMSAApp/">msal-authcode-api</a><br/>
 * Run:
 * <ol>
 *      <li>{@link MainWebApi} class</li>
 *      <li>Open <a href="http://localhost:35712/info_web_api"/>http://localhost:35712/info_web_api</a></li>
 * </ol>
 */
public class MainWebApi {
    public static void main(String[] args) throws Exception {
        var apiPath = "/me";
        var webPath = "/info_web_api";
        try (var apiApp = new ApiApp(API_APP_PORT, apiPath);
             var webApp = new WebApp(WEB_APP_PORT, AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                     WEB_APP_CLIENT_SECRET, TOKEN_AUTHORITY, apiApp.getBaseUrl() + apiPath,
                     webPath)) {
            apiApp.start();
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl + webPath);
            Thread.currentThread().join();
        }
    }
}
