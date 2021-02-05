package azure.flow.authcode.webapi;

import static java.lang.String.format;

/**
 * "Authentication Code Grant" flow example: Client + WebApp + ApiApp.<p/>
 * Run:
 * <ol>
 *      <li>@link Main}</li>
 *      <li>Open http://localhost:35712/info/</li>
 * </ol>
 */
public class MainWebApi {
    private final static String CLIENT_ID = "36922d24-27a7-4845-8978-c1935f155415";
    private final static String TENANT = "26376728-d7c9-4e0b-92b0-979740c8ac58";
    private final static String AUTHORITY = format("https://login.microsoftonline.com/%s/", TENANT);
    private final static String REDIRECT_URL = "http://localhost:35712/redirect";
    private final static int WEB_APP_PORT = 35712;

    public static void main(String[] args) throws Exception {
        try (var bankWebApp = new BankWebApp(WEB_APP_PORT, AUTHORITY, REDIRECT_URL, CLIENT_ID)) {
            bankWebApp.start();
            var baseUrl = bankWebApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.sleep(120000);
        }
    }
}
