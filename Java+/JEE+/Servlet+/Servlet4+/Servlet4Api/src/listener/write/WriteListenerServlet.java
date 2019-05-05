package listener.write;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WriteListenerServlet extends HttpServlet {
    static boolean onWritePossibleInvoked = false;
    static Throwable onErrorThrowable;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Start");
        WriteListener writeListener = new WriteListener() {
            @Override
            public void onWritePossible() {
                System.out.println("Write is possible");
                onWritePossibleInvoked = true;
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error!!!!");
                onErrorThrowable = t;
            }
        };

        final AsyncContext ctxt = req.startAsync();
        ctxt.start(() -> {
            try {
                System.out.println("Start request processing");
                ServletOutputStream out = resp.getOutputStream();
                out.setWriteListener(writeListener);
                while (!onWritePossibleInvoked) {
                    Thread.sleep(100);
                    System.out.println("Wait write is possible");
                }
                while (out.isReady() && onErrorThrowable == null) {
                    out.write("abc".getBytes());
                }
                System.out.println("Finish request processing");
                ctxt.complete();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }

}