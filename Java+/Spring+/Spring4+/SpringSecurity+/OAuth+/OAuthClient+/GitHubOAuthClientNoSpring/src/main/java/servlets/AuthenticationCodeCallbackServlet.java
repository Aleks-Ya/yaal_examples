package servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import util.OAuthUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import static servlets.TokenCallbackServlet.ACCESS_TOKEN_ATTR;

@WebServlet(name = "AuthenticationCodeCallbackServlet", urlPatterns = "/callback/authentication_code")
public class AuthenticationCodeCallbackServlet extends HttpServlet {
    public static final String AUTHORIZATION_CODE_ATTR = "AuthorizationCode";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String authorizationCode = request.getParameter("code");
        System.out.println("authentication code=" + authorizationCode);
        if (authorizationCode != null && !authorizationCode.isEmpty()) {
            request.getSession().setAttribute(AUTHORIZATION_CODE_ATTR, authorizationCode);
            String state = UUID.randomUUID().toString();
            String tokenRedirect = OAuthUtils.tokenEndpointUrl(authorizationCode, state);

            HttpURLConnection con = (HttpURLConnection) new URL(tokenRedirect).openConnection();
            con.setRequestProperty("Accept", "application/json");
            con.connect();
            InputStream content = (InputStream) con.getContent();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(content);
            String accessToken = root.at("/access_token").asText();
            con.disconnect();

            System.out.println("Access Token: " + accessToken);

            if (accessToken != null && !accessToken.isEmpty()) {
                request.getSession().setAttribute(ACCESS_TOKEN_ATTR, accessToken);
            } else {
                response.sendError(401, "Unauthorized AccessTokenCallbackServlet");
            }

        } else {
            response.sendError(401, "Unauthorized AuthenticationCodeCallbackServlet");
        }

    }
}
