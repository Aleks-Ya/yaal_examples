package azure.flow.authcode.web;

import static java.lang.String.format;

/**
 * "Authentication Code Grant" flow example: Client + WebApp.<p/>
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-app-types?WT.mc_id=Portal-Microsoft_AAD_RegisteredApps#web-apps">Docs</a><p/>
 * Run:
 * <ol>
 *      <li>@link Main}</li>
 *      <li>Open <a href="http://localhost:35712/info/"/>http://localhost:35712/info/</a></li>
 * </ol>
 */
public class MainWeb {
    private final static String CLIENT_ID = "36922d24-27a7-4845-8978-c1935f155415";
    private final static String CLIENT_SECRET = "8V3-KNVRb.He2RY~L08e37CBhB5EUE.V20";
    private final static String TENANT = "26376728-d7c9-4e0b-92b0-979740c8ac58";
    private final static String AUTHORITY = format("https://login.microsoftonline.com/%s/", TENANT);
    private final static String REDIRECT_URL = "http://localhost:35712/redirect";
    private final static int WEB_APP_PORT = 35712;
    private final static String GRAPH_ENDPOINT = "https://graph.microsoft.com/";

    public static void main(String[] args) throws Exception {
        try (var bankWebApp = new BankWebApp(WEB_APP_PORT, AUTHORITY, REDIRECT_URL, CLIENT_ID, CLIENT_SECRET, GRAPH_ENDPOINT)) {
            bankWebApp.start();
            var baseUrl = bankWebApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.sleep(120000);
        }
    }
}
