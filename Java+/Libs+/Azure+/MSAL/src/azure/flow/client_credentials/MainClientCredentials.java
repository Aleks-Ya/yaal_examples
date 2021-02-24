package azure.flow.client_credentials;

import static azure.flow.client_credentials.Constants.AUTHORITY;
import static azure.flow.client_credentials.Constants.ME_GRAPH_ENDPOINT;
import static azure.flow.client_credentials.Constants.WEB_APP_CLIENT_ID;
import static azure.flow.client_credentials.Constants.WEB_APP_CLIENT_SECRET;
import static azure.flow.client_credentials.Constants.WEB_APP_PORT;
import static azure.flow.client_credentials.Constants.WEB_APP_REDIRECT_URL;

/**
 * "Client Credentials" flow example.<br/>
 * Docs: <a href="https://docs.microsoft.com/en-us/azure/active-directory/develop/msal-authentication-flows#client-credentials">Client credentials</a><br/>
 * Azure App Registration: <a href="https://portal.azure.com/#blade/Microsoft_AAD_RegisteredApps/ApplicationMenuBlade/Overview/appId/26f901cc-2ca4-456f-9326-7d2630e6d6db/objectId/98bf7f4f-dce0-4202-8f25-8237085fe425/isMSAApp//defaultBlade/Overview/appSignInAudience/AzureADMyOrg/servicePrincipalCreated/true">msal-client-credentials-flow</a><br/>
 * Run:
 * <ol>
 *      <li>{@link MainClientCredentials} class</li>
 *      <li>Open <a href="http://localhost:35712/info_web_only"/>http://localhost:35712/info_web_only</a></li>
 * </ol>
 */
public class MainClientCredentials {
    public static void main(String[] args) throws Exception {
        try (var webApp = new WebApp(WEB_APP_PORT, AUTHORITY, WEB_APP_REDIRECT_URL, WEB_APP_CLIENT_ID,
                WEB_APP_CLIENT_SECRET, ME_GRAPH_ENDPOINT, "/info_web_only")) {
            webApp.start();
            var baseUrl = webApp.getBaseUrl();
            System.out.println(baseUrl);
            Thread.currentThread().join();
        }
    }
}
