package azure.flow.authcode.common;

import static java.lang.String.format;

public interface Constants {
    String TENANT = "26376728-d7c9-4e0b-92b0-979740c8ac58";
    String AUTHORITY = format("https://login.microsoftonline.com/%s/", TENANT);
    String TOKEN_AUTHORITY = format("https://login.microsoftonline.com/%s/oauth2/v2.0/token", TENANT);
    String ME_GRAPH_ENDPOINT = "https://graph.microsoft.com/v1.0/me";
    String WEB_APP_CLIENT_ID = "89f6d017-0ac1-413a-9e05-91ea9c8ada6a";
    String WEB_APP_CLIENT_SECRET = "M44g6_bolrpG_tkW2jr_LZEG~z.dw538a7";
    String API_APP_CLIENT_ID = "e95e7874-3073-4c5e-851c-84c68317cbd7";
    String API_APP_CLIENT_SECRET = "hMHqPs~.0yL~y333-fE~2RF365pHZ~K37-";
    int API_APP_PORT = 46823;
    int WEB_APP_PORT = 35712;
    String WEB_APP_REDIRECT_URL = format("http://localhost:%s/redirect", WEB_APP_PORT);
    String WEB_APP_PERMISSION = "api://msal-web-app-id/Read.ME";
}
