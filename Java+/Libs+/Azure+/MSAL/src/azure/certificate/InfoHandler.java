package azure.certificate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;

import static azure.certificate.HttpClientHelper.getFromUrl;
import static azure.certificate.SessionHelper.WEB_APP_ACCESS_TOKEN_ATTR;

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
        var accessToken = SessionHelper.getAccessTokenOrThrow(request, WEB_APP_ACCESS_TOKEN_ATTR);
        var me = getFromUrl(meGraphEndpoint, accessToken);
        response.getWriter().printf("<h1>%s</h1><p>%s</p>", message, me);
    }
}
