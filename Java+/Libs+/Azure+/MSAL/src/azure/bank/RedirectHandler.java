package azure.bank;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;

import static azure.bank.AuthHandler.AUTH_ATTR;

public class RedirectHandler extends AbstractHandler {
    public static final String REDIRECT_ENDPOINT = "/redirect";

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String authCode = request.getParameter("code");
        request.getSession().setAttribute(AUTH_ATTR, authCode);
        System.out.println("Auth Code: " + authCode);
        var targetPath = request.getParameter("state");
        response.sendRedirect(targetPath);
    }
}