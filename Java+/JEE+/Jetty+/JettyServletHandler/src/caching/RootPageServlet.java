package caching;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RootPageServlet extends HttpServlet {
    static final String CONTENT = "<html>" +
            "<head>" +
            "<script type='text/javascript' src='/static/fake.js'></script>" +
            "<script type='text/javascript' src='/cache/data'></script>" +
            "</head>" +
            "<body><h1>" + RootPageServlet.class.getName() + "</h1></body>" +
            "</html>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().append(CONTENT);
        resp.setContentType("text/html");
        resp.setHeader("Cache-Control", "no-cache");
    }

}