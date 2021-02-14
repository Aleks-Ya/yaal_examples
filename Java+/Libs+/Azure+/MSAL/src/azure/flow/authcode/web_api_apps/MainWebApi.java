package azure.flow.authcode.web_api_apps;

import static azure.flow.authcode.common.Constants.API_APP_PORT;
import static azure.flow.authcode.common.Constants.AUTHORITY;
import static azure.flow.authcode.common.Constants.TOKEN_AUTHORITY;
import static azure.flow.authcode.common.Constants.WEB_APP_CLIENT_ID;
import static azure.flow.authcode.common.Constants.WEB_APP_CLIENT_SECRET;
import static azure.flow.authcode.common.Constants.WEB_APP_PORT;
import static azure.flow.authcode.common.Constants.WEB_APP_REDIRECT_URL;

/**
 * "Authentication Code Grant" flow example: Client + WebApp.<p/>
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-app-types?WT.mc_id=Portal-Microsoft_AAD_RegisteredApps#web-apps">Docs</a><p/>
 * Run:
 * <ol>
 *      <li>{@link MainWebApi} class</li>
 *      <li>Open <a href="http://localhost:35712/info_web_api"/>http://localhost:35712/info_web_api</a></li>
 * </ol>
 */
public class MainWebApi {
    public static void main(String[] args) throws Exception {
        var apiPath = "/me";
        try (var apiApp = new ApiApp(API_APP_PORT, apiPath);
             var webApp = new WebApp(WEB_APP_PORT, AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                     WEB_APP_CLIENT_SECRET, TOKEN_AUTHORITY, apiApp.getBaseUrl() + apiPath,
                     "/info_web_api")) {
            apiApp.start();
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.currentThread().join();
        }
    }
}
