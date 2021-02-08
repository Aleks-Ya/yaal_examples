package azure.flow.authcode.webapi;

import static java.lang.String.format;

/**
 * "Authentication Code Grant" flow example: Client + WebApp + ApiApp.<p/>
 * <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-app-types?WT.mc_id=Portal-Microsoft_AAD_RegisteredApps#daemons-and-server-side-apps">Docs</a><p/>
 * Run:
 * <ol>
 *      <li>@link Main}</li>
 *      <li>Open <a href="http://localhost:46823/info/"/>http://localhost:46823/info/</a></li>
 * </ol>
 */
public class MainWebApi {
    private final static String WEB_APP_CLIENT_ID = "89f6d017-0ac1-413a-9e05-91ea9c8ada6a";
    private final static String API_APP_CLIENT_ID = "e95e7874-3073-4c5e-851c-84c68317cbd7";
    private final static String TENANT = "26376728-d7c9-4e0b-92b0-979740c8ac58";
    private final static String AUTHORITY = format("https://login.microsoftonline.com/%s/", TENANT);
    private final static String REDIRECT_URL = "http://localhost:35712/redirect";
    private final static int WEB_APP_PORT = 46823;
    private final static int API_APP_PORT = 46824;

    public static void main(String[] args) throws Exception {
        try (var bankApiApp = new BankApiApp(API_APP_PORT, AUTHORITY, REDIRECT_URL, API_APP_CLIENT_ID);
             var bankWebApp = new BankWebApp(WEB_APP_PORT, AUTHORITY, REDIRECT_URL, WEB_APP_CLIENT_ID)) {
            bankApiApp.start();
            bankWebApp.start();
            var baseUrl = bankWebApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.sleep(120000);
        }
    }
}
