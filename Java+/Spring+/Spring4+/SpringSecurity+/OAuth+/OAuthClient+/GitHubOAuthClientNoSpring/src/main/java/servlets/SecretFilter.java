package servlets;

import util.OAuthUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "SecretFilter", urlPatterns = "/secret/*")
public class SecretFilter implements Filter {
    private static final String AUTHORIZATION_CODE_ATTR = "authorizationCode";
    static final String ACCESS_TOKEN_ATTR = "accessToken";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) resp;
        String accessToken = (String) httpReq.getSession().getAttribute(ACCESS_TOKEN_ATTR);
        if (accessToken != null && !accessToken.isEmpty()) {
            chain.doFilter(req, resp);
        } else {
            String authenticationCode = getAuthenticationCode(httpReq);
            if (authenticationCode != null) {
                String accessT = OAuthUtils.requestAccessToken(authenticationCode);
                if (accessT != null && !accessT.isEmpty()) {
                    httpReq.getSession().setAttribute(ACCESS_TOKEN_ATTR, accessT);
                    chain.doFilter(req, resp);
                } else {
                    httpRes.sendError(401, "Unauthorized AccessTokenCallbackServlet");
                }
            } else {
                String path = httpReq.getServletPath();
                String location = OAuthUtils.authorizationEndpointUrl(path);
                httpRes.sendRedirect(location);
            }
        }
    }

    private String getAuthenticationCode(HttpServletRequest httpReq) {
        String responseValue = httpReq.getParameter("code");
        if (responseValue == null) {
            responseValue = (String) httpReq.getSession().getAttribute(AUTHORIZATION_CODE_ATTR);
        } else {
            httpReq.getSession().setAttribute(AUTHORIZATION_CODE_ATTR, responseValue);
        }
        return responseValue;
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
