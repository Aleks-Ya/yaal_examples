package azure.certificate;

import static java.lang.String.format;

interface Constants {
    String TENANT = "26376728-d7c9-4e0b-92b0-979740c8ac58";
    String AUTHORITY = format("https://login.microsoftonline.com/%s/", TENANT);
    String ME_GRAPH_ENDPOINT = "https://graph.microsoft.com/v1.0/me";
    String WEB_APP_CLIENT_ID = "525a5fa1-25f1-4586-8b20-88b89c65ab8a";
    String WEB_APP_CLIENT_CERT_FILE = "msal-web-signed.p12";
    String WEB_APP_CLIENT_CERT_PASSWORD = "";
    int WEB_APP_PORT = 31235;
    String WEB_APP_REDIRECT_URL = format("http://localhost:%s/redirect", WEB_APP_PORT);
}
