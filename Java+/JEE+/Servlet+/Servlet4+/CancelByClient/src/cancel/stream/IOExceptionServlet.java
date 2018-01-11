package cancel.stream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Use catching IOException for detection that client canceled the request.
 */
public class IOExceptionServlet extends HttpServlet {
    static final StringBuilder wroteText = new StringBuilder();
    static Throwable error;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("Start");
        ServletOutputStream out = resp.getOutputStream();
        long n = 0;
        do {
            String text = String.valueOf(n) + " ";
            wroteText.append(text);
            try {
                out.print(text);
                out.flush();
            } catch (IOException e) {
                error = e;
                System.out.println("Finish");
                return;
            }
            n++;
        } while (true);
    }

}