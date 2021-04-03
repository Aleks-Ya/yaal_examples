package azure.claim.web_claim;

import com.nimbusds.jwt.JWTParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.text.ParseException;

import static azure.claim.web_claim.SessionHelper.WEB_APP_ACCESS_TOKEN_ATTR;

class ShowTokenHandler extends AbstractHandler {
    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            var accessToken = SessionHelper.getAccessTokenOrThrow(request, WEB_APP_ACCESS_TOKEN_ATTR);
            var jwt = JWTParser.parse(accessToken);
            var authTime = jwt.getJWTClaimsSet().getDateClaim("auth_time");
            var headerStr = jwt.getHeader().toJSONObject().toString();
            var claimsStr = jwt.getJWTClaimsSet().toJSONObject().toString();
            response.getWriter().printf("<h1>Access Token</h1><p>auth_time claim: %s</p><p>Headers: %s</p><p>Claims: %s</p>",
                    authTime, headerStr, claimsStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
