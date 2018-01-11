package cancel.stream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Use ServletOutputStream#setWriteListener method for detection that client canceled the request.
 */
public class WriteListenerServlet extends HttpServlet {
    static final StringBuilder wroteText = new StringBuilder();
    static Throwable error;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("Start");
        ServletOutputStream out = resp.getOutputStream();
        WriteListener writeListener = new WriteListener() {
            @Override
            public void onWritePossible() {
                System.out.println("Write possible");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error!!!!");
                error = t;
            }
        };
        out.setWriteListener(writeListener);
        long n = 0;
        do {
            String text = String.valueOf(n) + " ";
            wroteText.append(text);
            out.print(text);
        } while (error != null);
        System.out.println("Finish");
    }

}