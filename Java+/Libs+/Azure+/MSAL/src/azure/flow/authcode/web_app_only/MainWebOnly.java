package azure.flow.authcode.web_app_only;

import static azure.flow.authcode.common.Constants.AUTHORITY;
import static azure.flow.authcode.common.Constants.ME_GRAPH_ENDPOINT;
import static azure.flow.authcode.common.Constants.WEB_APP_CLIENT_ID;
import static azure.flow.authcode.common.Constants.WEB_APP_CLIENT_SECRET;
import static azure.flow.authcode.common.Constants.WEB_APP_PORT;
import static azure.flow.authcode.common.Constants.WEB_APP_REDIRECT_URL;

/**
 * "Authentication Code Grant" flow example: Client + WebApp.<p/>
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-app-types?WT.mc_id=Portal-Microsoft_AAD_RegisteredApps#web-apps">Docs</a><p/>
 * Run:
 * <ol>
 *      <li>{@link MainWebOnly} class</li>
 *      <li>Open <a href="http://localhost:35712/info_web_only"/>http://localhost:35712/info_web_only</a></li>
 * </ol>
 */
public class MainWebOnly {
    public static void main(String[] args) throws Exception {
        try (var webApp = new WebApp(WEB_APP_PORT, AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                WEB_APP_CLIENT_SECRET, ME_GRAPH_ENDPOINT, "/info_web_only")) {
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.sleep(240000);
        }
    }
}
