package servlets;

import util.GitHubUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "GitHubUserInfoServlet", urlPatterns = "/secret/user_info")
public class GitHubUserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accessToken = (String) request.getSession().getAttribute(TokenCallbackServlet.ACCESS_TOKEN_ATTR);
        if (accessToken == null) {
            response.sendError(401, "No access token");
        }
        HttpURLConnection con = (HttpURLConnection) new URL(GitHubUtils.userInfoUrl(accessToken)).openConnection();
        con.connect();
        Object content = con.getContent();
        System.out.println("User info: " + content);
        con.disconnect();
    }
}
