package azure.flow.authcode.web_api_graph_apps;

import azure.flow.authcode.common.Constants;

import static azure.flow.authcode.common.Constants.API_APP_CLIENT_ID;
import static azure.flow.authcode.common.Constants.API_APP_CLIENT_SECRET;
import static azure.flow.authcode.common.Constants.AUTHORITY;
import static azure.flow.authcode.common.Constants.ME_GRAPH_ENDPOINT;
import static azure.flow.authcode.common.Constants.WEB_APP_CLIENT_ID;
import static azure.flow.authcode.common.Constants.WEB_APP_CLIENT_SECRET;

/**
 * "Authentication Code Grant" flow example: Client + WebApp.<p/>
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-app-types?WT.mc_id=Portal-Microsoft_AAD_RegisteredApps#web-apps">Docs</a><p/>
 * Run:
 * <ol>
 *      <li>{@link MainWebApiGraph} class</li>
 *      <li>Open <a href="http://localhost:35712/info_web_api_graph"/>http://localhost:35712/info_web_api_graph</a></li>
 * </ol>
 */
public class MainWebApiGraph {
    public static void main(String[] args) throws Exception {
        try (var apiApp = new ApiApp(Constants.API_APP_PORT, Constants.TOKEN_AUTHORITY, API_APP_CLIENT_ID,
                API_APP_CLIENT_SECRET, ME_GRAPH_ENDPOINT);
             var webApp = new WebApp(Constants.WEB_APP_PORT, AUTHORITY, Constants.WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                     WEB_APP_CLIENT_SECRET, Constants.TOKEN_AUTHORITY, apiApp.getBaseUrl() + "/me",
                     "/info_web_api_graph")) {
            apiApp.start();
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.currentThread().join();
        }
    }
}
