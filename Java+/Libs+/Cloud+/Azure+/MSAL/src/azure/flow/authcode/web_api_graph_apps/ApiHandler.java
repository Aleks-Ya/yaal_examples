package azure.flow.authcode.web_api_graph_apps;

import azure.flow.authcode.common.SessionHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.util.Set;

import static azure.flow.authcode.common.HttpClientHelper.getFromUrl;
import static azure.flow.authcode.common.HttpClientHelper.requestOboAccessToken;
import static azure.flow.authcode.common.SessionHelper.WEB_APP_ACCESS_TOKEN_ATTR;
import static azure.flow.authcode.web_api_graph_apps.ApiApp.API_APP_SCOPE;

class ApiHandler extends AbstractHandler {
    private final String message;
    private final String webAppClientId;
    private final String webAppClientSecret;
    private final String apiAppAuthority;
    private final String apiAppUrl;

    ApiHandler(String message, String webAppClientId, String webAppClientSecret,
               String apiAppAuthority, String apiAppUrl) {
        this.message = message;
        this.webAppClientId = webAppClientId;
        this.webAppClientSecret = webAppClientSecret;
        this.apiAppAuthority = apiAppAuthority;
        this.apiAppUrl = apiAppUrl;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        var scopes = Set.of(API_APP_SCOPE);
        var userAccessToken = SessionHelper.getAccessTokenOrThrow(request, WEB_APP_ACCESS_TOKEN_ATTR);
        var apiAccessToken = requestOboAccessToken(webAppClientId, webAppClientSecret, apiAppAuthority,
                scopes, userAccessToken);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        var me = getFromUrl(apiAppUrl, apiAccessToken);
        response.getWriter().printf("<h1>%s</h1><p>%s</p>", message, me);
    }
}
