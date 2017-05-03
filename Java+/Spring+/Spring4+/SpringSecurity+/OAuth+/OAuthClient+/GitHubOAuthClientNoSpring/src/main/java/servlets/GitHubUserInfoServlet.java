package servlets;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.UserInfo;
import util.GitHubUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "GitHubUserInfoServlet", urlPatterns = "/secret/user_info")
public class GitHubUserInfoServlet extends HttpServlet {
    private static final String USER_INFO_ATTR = "userInfoAttr";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accessToken = (String) request.getSession().getAttribute(SecretFilter.ACCESS_TOKEN_ATTR);
        if (accessToken == null) {
            response.sendError(401, "No access token");
        }
        HttpURLConnection con = (HttpURLConnection) new URL(GitHubUtils.userInfoUrl(accessToken)).openConnection();
        con.connect();
        InputStream content = (InputStream) con.getContent();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        UserInfo userInfo = mapper.readValue(content, UserInfo.class);
        request.setAttribute(USER_INFO_ATTR, userInfo);
        System.out.println("User info: " + userInfo);
        con.disconnect();
        request.getRequestDispatcher("/secret/user_info.jsp").forward(request, response);
    }
}
