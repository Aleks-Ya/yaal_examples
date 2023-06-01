package servlets;

import storage.FileStorage;
import storage.Storage;
import util.OAuthUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebFilter(filterName = "SecretFilter", urlPatterns = "/secret/*")
public class SecretFilter implements Filter {
    static final String ACCESS_TOKEN_ATTR = "accessToken";
    private static final String AUTHORIZATION_CODE_ATTR = "authorizationCode";
    private static final Storage storage;

    static {
        try {
            File file = new File(System.getProperty("java.io.tmpdir"), "secret_storage.txt");
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            }
            System.out.println("Secret file: " + file.getAbsolutePath());
            storage = new FileStorage(file);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) resp;
        String accessToken = getAccessToken(httpReq);
        if (accessToken != null && !accessToken.isEmpty()) {
            chain.doFilter(req, resp);
        } else {
            String authenticationCode = getAuthenticationCode(httpReq);
            if (authenticationCode != null) {
                accessToken = OAuthUtils.requestAccessToken(authenticationCode);
                if (accessToken != null && !accessToken.isEmpty()) {
                    setAccessToken(httpReq, accessToken);
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

    private String getAccessToken(HttpServletRequest httpReq) {
        HttpSession session = httpReq.getSession();
        String accessToken = (String) session.getAttribute(ACCESS_TOKEN_ATTR);
        if (accessToken == null) {
            accessToken = storage.readAccessToken(session.getId());
            if (accessToken != null) {
                session.setAttribute(ACCESS_TOKEN_ATTR, accessToken);
            }
        }
        return accessToken;
    }

    private void setAccessToken(HttpServletRequest httpReq, String accessToken) {
        httpReq.getSession().setAttribute(ACCESS_TOKEN_ATTR, accessToken);
        storage.saveAccessToken(httpReq.getSession().getId(), accessToken);
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

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
