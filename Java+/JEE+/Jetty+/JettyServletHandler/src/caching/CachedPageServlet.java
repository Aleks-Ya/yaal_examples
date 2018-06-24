package caching;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CachedPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().append(getClass().getName());
        resp.getWriter().append("<br/>");
        resp.getWriter().append("<a href='/cache/data'>Data");
        resp.setHeader("Cache-Control", "max-age=31536000");
        resp.setHeader("Expires", "Mon, 25 Jun 2020 21:31:12 GMT");
    }

}