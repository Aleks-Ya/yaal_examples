package servlet4.cancel_by_client.writer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Use java.io.PrintWriter#checkError method for detection that client canceled the request.
 */
public class WithWritingServlet extends HttpServlet {
    static boolean checkError;
    static final StringBuilder wroteText = new StringBuilder();
    static long writeCount;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("Start never endless download");
        PrintWriter writer = resp.getWriter();
        checkError = writer.checkError();
        writeCount = 0;
        do {
            String text = String.valueOf(writeCount) + " ";
            writer.write(text);
            wroteText.append(text);
            writeCount++;
            checkError = writer.checkError();
        } while (!checkError);
        System.out.println("Finish never endless download. checkError=" + writer.checkError());
    }

}