package azure.flow.authcode.web_app_only;

import azure.flow.authcode.common.SessionHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class InfoHandler extends AbstractHandler {
    private final String message;
    private final String meGraphEndpoint;

    InfoHandler(String message, String meGraphEndpoint) {
        this.message = message;
        this.meGraphEndpoint = meGraphEndpoint;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        var accessToken = SessionHelper.getAccessTokenOrThrow(request);
        var me = getUserInfoFromGraph(accessToken);
        response.getWriter().printf("<h1>%s</h1><p>%s</p>", message, me);
    }

    private String getUserInfoFromGraph(String accessToken) throws IOException {
        // Microsoft Graph user endpoint
        var url = new URL(meGraphEndpoint);
        var conn = (HttpURLConnection) url.openConnection();

        // Set the appropriate header fields in the request header.
        conn.setRequestProperty("Authorization", "Bearer " + accessToken);
        conn.setRequestProperty("Accept", "application/json");

        var response = getResponseStringFromConn(conn);

        var responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException(response);
        }
        return response;
    }

    static String getResponseStringFromConn(HttpURLConnection conn) throws IOException {
        BufferedReader reader;
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        var stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}