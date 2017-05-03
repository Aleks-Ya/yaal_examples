package util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import static java.lang.String.format;

public class OAuthUtils {

    private static final String authorizationEndpoint = "https://github.com/login/oauth/authorize";
    private static final String tokenEndpoint = "https://github.com/login/oauth/access_token";
    private static final String clientId = "0faacddff490645e6e3d";
    private static final String clientSecret = "afe8fb0845df2ea1a0251d27e0ff7091c7ff7b3c";
    private static final String authenticationCodeRedirectUrl = "http://46.151.9.25:8080/hi";
    private static final String accessTokenRedirectUrl = "http://46.151.9.25:8080/hi/callback/access_token";

    public static String authorizationEndpointUrl(String path) {
        return format("%s?client_id=%s&redirect_uri=%s",
                authorizationEndpoint, clientId, authenticationCodeRedirectUrl + path);
    }

    private static String tokenEndpointUrl(String authenticationCode, String state) {
        return format("%s?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s&state=%s",
                tokenEndpoint, clientId, clientSecret, accessTokenRedirectUrl, authenticationCode, state);
    }

    public static String requestAccessToken(String authorizationCode) throws IOException {
        String state = UUID.randomUUID().toString();
        String tokenRedirect = tokenEndpointUrl(authorizationCode, state);

        HttpURLConnection con = (HttpURLConnection) new URL(tokenRedirect).openConnection();
        con.setRequestProperty("Accept", "application/json");
        con.connect();
        InputStream content = (InputStream) con.getContent();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(content);
        String accessToken = root.at("/access_token").asText();
        con.disconnect();

        System.out.println("Access Token: " + accessToken);
        return accessToken;
    }
}
