package handler.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.HandlerWrapper;

import java.io.IOException;

class AuthFilterHandler extends HandlerWrapper {
    static final String AUTH_ATTR = "AUTH";

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        var authAttribute = request.getAttribute(AUTH_ATTR);
        if (authAttribute == null) {
            request.setAttribute(AUTH_ATTR, "authorized");
            System.out.println("Request is authorized");
        } else {
            System.out.println("Request is already authorized");
        }
        super.handle(target, baseRequest, request, response);
    }
}