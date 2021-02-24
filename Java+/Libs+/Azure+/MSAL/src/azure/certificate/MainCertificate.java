package azure.certificate;

import java.io.File;

import static azure.certificate.Constants.AUTHORITY;
import static azure.certificate.Constants.ME_GRAPH_ENDPOINT;
import static azure.certificate.Constants.WEB_APP_CLIENT_CERT_FILE;
import static azure.certificate.Constants.WEB_APP_CLIENT_CERT_PASSWORD;
import static azure.certificate.Constants.WEB_APP_CLIENT_ID;
import static azure.certificate.Constants.WEB_APP_PORT;
import static azure.certificate.Constants.WEB_APP_REDIRECT_URL;

/**
 * Web-application signed with a certificate.<br/>
 * Azure App Registration: <a href="https://portal.azure.com/#blade/Microsoft_AAD_RegisteredApps/ApplicationMenuBlade/Overview/appId/525a5fa1-25f1-4586-8b20-88b89c65ab8a/isMSAApp/">msal-certificate</a><br/>
 * Run:
 * <ol>
 *      <li>Run {@link MainCertificate} class</li>
 *      <li>Open <a href="http://localhost:31235/info"/>http://localhost:31235/info</a></li>
 * </ol>
 */
public class MainCertificate {
    public static void main(String[] args) throws Exception {
        var clientCertFile = new File(MainCertificate.class.getResource(WEB_APP_CLIENT_CERT_FILE).getFile());
        try (var webApp = new WebApp(WEB_APP_PORT, AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                clientCertFile, WEB_APP_CLIENT_CERT_PASSWORD, ME_GRAPH_ENDPOINT, "/info")) {
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.currentThread().join();
        }
    }
}
