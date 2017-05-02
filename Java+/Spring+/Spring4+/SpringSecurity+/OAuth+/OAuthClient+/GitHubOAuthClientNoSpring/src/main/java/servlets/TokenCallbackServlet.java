package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AccessTokenCallbackServlet", urlPatterns = "/callback/access_token")
public class TokenCallbackServlet extends HttpServlet {
    public static final String ACCESS_TOKEN_ATTR = "AccessToken";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accessToken = request.getParameter("access_token");
        System.out.println("accessToken=" + accessToken);
        if (accessToken != null && !accessToken.isEmpty()) {
            request.getSession().setAttribute(ACCESS_TOKEN_ATTR, accessToken);
        } else {
            response.sendError(401, "Unauthorized AccessTokenCallbackServlet");
        }

    }
}
