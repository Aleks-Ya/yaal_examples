package azure.flow.authcode.web_app_only;

import static java.lang.String.format;

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
    private final static int WEB_APP_PORT = 35712;
    private final static String WEB_APP_CLIENT_ID = "89f6d017-0ac1-413a-9e05-91ea9c8ada6a";
    private final static String WEB_APP_CLIENT_SECRET = "M44g6_bolrpG_tkW2jr_LZEG~z.dw538a7";
    private final static String WEB_APP_TENANT = "26376728-d7c9-4e0b-92b0-979740c8ac58";
    private final static String WEB_APP_AUTHORITY = format("https://login.microsoftonline.com/%s/", WEB_APP_TENANT);
    private final static String WEB_APP_REDIRECT_URL = "http://localhost:35712/redirect";

    private final static String ME_GRAPH_ENDPOINT = "https://graph.microsoft.com/v1.0/me";

    public static void main(String[] args) throws Exception {
        try (var webApp = new WebApp(WEB_APP_PORT, WEB_APP_AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                WEB_APP_CLIENT_SECRET, ME_GRAPH_ENDPOINT)) {
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.sleep(120000);
        }
    }
}