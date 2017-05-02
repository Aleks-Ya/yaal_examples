package util;

import static java.lang.String.format;

public class OAuthUtils {

    private static final String authorizationEndpoint = "https://github.com/login/oauth/authorize";
    private static final String tokenEndpoint = "https://github.com/login/oauth/access_token";
    private static final String clientId = "0faacddff490645e6e3d";
    private static final String clientSecret = "afe8fb0845df2ea1a0251d27e0ff7091c7ff7b3c";
    private static final String authenticationCodeRedirectUrl = "http://46.151.9.25:8080/hi/callback/authentication_code";
    private static final String accessTokenRedirectUrl = "http://46.151.9.25:8080/hi/callback/access_token";

    public static String authorizationEndpointUrl() {
        return format("%s?client_id=%s&client_secret=%s&redirect_uri=%s",
                authorizationEndpoint, clientId, clientSecret, authenticationCodeRedirectUrl);
    }

    public static String tokenEndpointUrl(String authenticationCode, String state) {
        return format("%s?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s&state=%s",
                tokenEndpoint, clientId, clientSecret, accessTokenRedirectUrl, authenticationCode, state);
    }

}
