package error.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Use java.io.PrintWriter#checkError method for detection that client canceled the request.
 */
public class SendDataForeverServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("Start never endless download");
        PrintWriter writer = resp.getWriter();
        long n = 0;
        while (!writer.checkError()) {
            writer.write(String.valueOf(n) + " ");
            n++;
        }
        System.out.println("Finish never endless download. checkError=" + writer.checkError());
    }

}