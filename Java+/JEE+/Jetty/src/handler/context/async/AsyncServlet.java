package handler.context.async;


import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Queue an async request from main thread: " + Thread.currentThread().getId());
        final AsyncContext ctxt = req.startAsync();
        ctxt.start(() -> {
            try {
                resp.setContentType("text/html");
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().append("Async respond body");
                System.out.println("Perform async request in other thread: " + Thread.currentThread().getId());
                ctxt.complete();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }
}