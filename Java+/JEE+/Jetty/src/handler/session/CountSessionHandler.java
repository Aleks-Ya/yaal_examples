package handler.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;

class CountSessionHandler extends AbstractHandler {
    private static final String COUNTER_ATTRIBUTE = "counter";
    private HttpSession session;

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        Integer counter = incrementCounter(request);
        response.getWriter().printf("Session: counter=%s", counter);
        baseRequest.setHandled(true);
    }

    private Integer incrementCounter(HttpServletRequest request) {
        session = request.getSession();
        var counter = (Integer) session.getAttribute(COUNTER_ATTRIBUTE);
        counter = counter != null ? counter + 1 : 1;
        session.setAttribute(COUNTER_ATTRIBUTE, counter);
        return counter;
    }

    public HttpSession getSession() {
        return session;
    }
}