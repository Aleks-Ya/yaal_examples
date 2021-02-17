package azure.certificate.web_signed;

import java.io.File;

import static azure.certificate.web_signed.Constants.AUTHORITY;
import static azure.certificate.web_signed.Constants.ME_GRAPH_ENDPOINT;
import static azure.certificate.web_signed.Constants.WEB_APP_CLIENT_CERT_FILE;
import static azure.certificate.web_signed.Constants.WEB_APP_CLIENT_CERT_PASSWORD;
import static azure.certificate.web_signed.Constants.WEB_APP_CLIENT_ID;
import static azure.certificate.web_signed.Constants.WEB_APP_PORT;
import static azure.certificate.web_signed.Constants.WEB_APP_REDIRECT_URL;

/**
 * Web-application signed with a certificate.
 * Run:
 * <ol>
 *      <li>{@link MainWebSigned} class</li>
 *      <li>Open <a href="http://localhost:31235/info"/>http://localhost:31235/info</a></li>
 * </ol>
 */
public class MainWebSigned {
    public static void main(String[] args) throws Exception {
        var clientCertFile = new File(MainWebSigned.class.getResource(WEB_APP_CLIENT_CERT_FILE).getFile());
        try (var webApp = new WebApp(WEB_APP_PORT, AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                clientCertFile, WEB_APP_CLIENT_CERT_PASSWORD, ME_GRAPH_ENDPOINT, "/info")) {
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.currentThread().join();
        }
    }
}
