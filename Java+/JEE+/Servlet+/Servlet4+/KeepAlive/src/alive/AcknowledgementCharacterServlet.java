package alive;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AcknowledgementCharacterServlet extends HttpServlet {
    static long writeCount;
    static final long KEEP_ALIVE_SEC = 10;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            System.out.println("Start never endless download");
            PrintWriter writer = resp.getWriter();
            writeCount = 0;
            char ackChar = '\u2406';
            long startTime = System.currentTimeMillis();
            do {
                writer.write(ackChar);
                Thread.sleep(100);
                writeCount++;
            } while ((System.currentTimeMillis() - startTime) / 1000 < KEEP_ALIVE_SEC);
            System.out.println("Finish never endless download. checkError=" + writer.checkError());
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}