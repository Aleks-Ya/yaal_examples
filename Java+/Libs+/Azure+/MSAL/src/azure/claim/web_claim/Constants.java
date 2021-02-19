package azure.claim.web_claim;

import static java.lang.String.format;

public interface Constants {
    String TENANT = "26376728-d7c9-4e0b-92b0-979740c8ac58";
    String AUTHORITY = format("https://login.microsoftonline.com/%s/", TENANT);
    String WEB_APP_CLIENT_ID = "22325e20-04f1-4977-bf33-cba197d0668f";
    String WEB_APP_CLIENT_SECRET = "st~VBs4Y18.~JtVmuU1W569cki~241R_-u";
    int WEB_APP_PORT = 31236;
    String WEB_APP_REDIRECT_URL = format("http://localhost:%s/redirect", WEB_APP_PORT);
    String WEB_APP_CALL_API_SCOPE = "api://msal-web-claim-id/API.Call";
}
