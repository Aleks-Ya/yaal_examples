package caching;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RootPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String etag = "abcdefg";
        String ifNotMatch = req.getHeader("If-None-Match");
        if (etag.equals(ifNotMatch)) {
            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            System.out.println("Not modified");
            return;
        } else {
            System.out.println("Modified");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().append("<html>" +
                    "<head>" +
                    "<script type='text/javascript' src='/static/fake.js'>" +
                    "</script>" +
                    "</head>" +
                    "<body><h1>" + getClass().getName() + "</h1></body>" +
                    "</html>");
        }
        resp.setContentType("text/html");
        resp.setHeader("Cache-Control", "max-age=3600");
//        resp.setHeader("Last-Modified", "Sun, 20 Jun 2018 21:03:18 GMT");
//        resp.setHeader("Expires", "Mon, 26 Jun 2020 21:31:12 GMT");
        resp.setHeader("ETag", etag);
//        resp.setHeader("Age", "31536000");
    }

}