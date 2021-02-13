package azure.flow.authcode.web_api_graph_apps;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.util.Set;

import static azure.flow.authcode.common.HttpClientHelper.getFromUrl;
import static azure.flow.authcode.common.HttpClientHelper.requestOboAccessToken;
import static azure.flow.authcode.common.AuthFilter.GRAPH_USER_READ_SCOPE;

class OboInfoHandler extends AbstractHandler {
    private final String message;
    private final String meGraphEndpoint;
    private final String msGraphAuthority;
    private final String apiAppClientId;
    private final String apiAppClientSecret;

    OboInfoHandler(String message, String meGraphEndpoint, String msGraphAuthority, String apiAppClientId, String apiAppClientSecret) {
        this.message = message;
        this.meGraphEndpoint = meGraphEndpoint;
        this.msGraphAuthority = msGraphAuthority;
        this.apiAppClientId = apiAppClientId;
        this.apiAppClientSecret = apiAppClientSecret;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        var scopes = Set.of(GRAPH_USER_READ_SCOPE);
        var authorizationHeader = request.getHeader("Authorization");
        var userAccessToken = authorizationHeader.split(" ")[1];
        var accessToken = requestOboAccessToken(apiAppClientId, apiAppClientSecret, msGraphAuthority,
                scopes, userAccessToken);
        var me = getFromUrl(meGraphEndpoint, accessToken);
        response.getWriter().printf("<h1>%s</h1><p>%s</p>", message, me);
    }
}
