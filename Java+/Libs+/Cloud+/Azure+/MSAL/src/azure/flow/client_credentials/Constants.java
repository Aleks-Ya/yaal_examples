package azure.flow.client_credentials;

import static java.lang.String.format;

interface Constants {
    String TENANT = "26376728-d7c9-4e0b-92b0-979740c8ac58";
    String AUTHORITY = format("https://login.microsoftonline.com/%s/", TENANT);
    String USERS_GRAPH_ENDPOINT = "https://graph.microsoft.com/v1.0/users";
    String DEFAULT_GRAPH_SCOPE = "https://graph.microsoft.com/.default";
    String CLIENT_ID = "26f901cc-2ca4-456f-9326-7d2630e6d6db";
    String CLIENT_SECRET = "lgRkcQ.mg2SNR3.UP1id7bl_.R77r~eNOn";
    int WEB_APP_PORT = 35713;
    String WEB_PATH = "/users";
}
