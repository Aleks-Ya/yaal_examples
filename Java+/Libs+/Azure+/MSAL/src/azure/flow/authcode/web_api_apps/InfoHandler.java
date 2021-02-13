package azure.flow.authcode.web_api_apps;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;

class InfoHandler extends AbstractHandler {
    private final String message;

    InfoHandler(String message) {
        this.message = message;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        var token = parseToken(request);
        response.getWriter().printf("<h1>%s</h1><p>%s</p>", message, token);
    }

    private static String parseToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            throw new IllegalStateException("Request has no Authorization header");
        }
        var split = authHeader.split(" ");
        if (split.length != 2) {
            throw new IllegalStateException("Invalid Authorization header");
        }
        var token = split[1];
        if (token == null || token.isBlank()) {
            throw new IllegalStateException("Wrong token: " + token);
        }
        return token;
    }

}
