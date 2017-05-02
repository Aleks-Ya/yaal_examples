package servlets;

import util.OAuthUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "SecretFilter", urlPatterns = "/secret/*")
public class SecretFilter implements Filter {


    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        String accessToken = (String) httpReq.getSession().getAttribute(TokenCallbackServlet.ACCESS_TOKEN_ATTR);
        if (accessToken != null && !accessToken.isEmpty()) {
            chain.doFilter(req, resp);
        } else {
            HttpServletResponse httpResp = (HttpServletResponse) resp;
            String location = OAuthUtils.authorizationEndpointUrl();
            httpResp.sendRedirect(location);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
