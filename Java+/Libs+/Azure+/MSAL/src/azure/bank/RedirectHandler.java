package azure.bank;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;

public class RedirectHandler extends AbstractHandler {
    private String authCode;

    RedirectHandler() {
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        authCode = request.getParameter("code");
        System.out.println("Auth Code: " + authCode);
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().printf("<h1>Auth Code: %s</h1>", authCode);
    }

    public String getAuthCode() {
        return authCode;
    }
}