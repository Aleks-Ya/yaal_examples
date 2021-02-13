package azure.flow.authcode.web_api_apps;

import azure.flow.authcode.common.SessionHelper;
import com.nimbusds.jwt.JWTParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.text.ParseException;

import static azure.flow.authcode.common.SessionHelper.WEB_APP_ACCESS_TOKEN_ATTR;

class InfoHandler extends AbstractHandler {
    private final String message;

    InfoHandler(String message) {
        this.message = message;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            var accessToken = SessionHelper.getAccessTokenOrThrow(request, WEB_APP_ACCESS_TOKEN_ATTR);
            var parseToken = JWTParser.parse(accessToken);
            response.getWriter().printf("<h1>%s</h1><p>%s</p>", message, parseToken);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
