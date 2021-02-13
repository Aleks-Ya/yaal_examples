package azure.flow.authcode.web_api_apps;

import static java.lang.String.format;

/**
 * "Authentication Code Grant" flow example: Client + WebApp.<p/>
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-app-types?WT.mc_id=Portal-Microsoft_AAD_RegisteredApps#web-apps">Docs</a><p/>
 * Run:
 * <ol>
 *      <li>{@link MainWebAndApi} class</li>
 *      <li>Open <a href="http://localhost:35712/info_web_only"/>http://localhost:35712/info_web_only</a></li>
 * </ol>
 */
public class MainWebAndApi {
    private final static int API_APP_PORT = 46823;
    private final static String API_APP_TENANT = "26376728-d7c9-4e0b-92b0-979740c8ac58";
    private final static String TOKEN_AUTHORITY = format("https://login.microsoftonline.com/%s/oauth2/v2.0/token", API_APP_TENANT);
    private final static String API_APP_CLIENT_ID = "e95e7874-3073-4c5e-851c-84c68317cbd7";
    private final static String API_APP_CLIENT_SECRET = "hMHqPs~.0yL~y333-fE~2RF365pHZ~K37-";

    private final static int WEB_APP_PORT = 35712;
    private final static String WEB_APP_CLIENT_ID = "89f6d017-0ac1-413a-9e05-91ea9c8ada6a";
    private final static String WEB_APP_CLIENT_SECRET = "M44g6_bolrpG_tkW2jr_LZEG~z.dw538a7";
    private final static String WEB_APP_TENANT = API_APP_TENANT;
    private final static String WEB_APP_AUTHORITY = format("https://login.microsoftonline.com/%s/", WEB_APP_TENANT);
    private final static String WEB_APP_REDIRECT_URL = "http://localhost:35712/redirect";

    private final static String ME_GRAPH_ENDPOINT = "https://graph.microsoft.com/v1.0/me";

    public static void main(String[] args) throws Exception {
        try (var apiApp = new ApiApp(API_APP_PORT, TOKEN_AUTHORITY, API_APP_CLIENT_ID,
                API_APP_CLIENT_SECRET, ME_GRAPH_ENDPOINT);
             var webApp = new WebApp(WEB_APP_PORT, WEB_APP_AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                     WEB_APP_CLIENT_SECRET, TOKEN_AUTHORITY, apiApp.getBaseUrl() + "/me")) {
            apiApp.start();
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.sleep(120000);
        }
    }
}
