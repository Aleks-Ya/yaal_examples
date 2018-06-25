package caching;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CachedPageServlet extends HttpServlet {
            static final String CONTENT = "console.log('hi from servlet JS');";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String etag = "abcdefg";
        String ifNotMatch = req.getHeader("If-None-Match");
        if (etag.equals(ifNotMatch)) {
            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            System.out.println("Not modified");
        } else {
            System.out.println("Modified");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().append(CONTENT);
            resp.setContentType("application/javascript");
        }
        resp.setHeader("Cache-Control", "max-age=120");
        resp.setHeader("ETag", etag);
//        resp.setHeader("Last-Modified", "Sun, 20 Jun 2018 21:03:18 GMT");
    }

}