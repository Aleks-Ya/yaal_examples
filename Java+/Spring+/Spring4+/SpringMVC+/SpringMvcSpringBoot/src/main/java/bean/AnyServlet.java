package bean;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Проверяет инициализацию бинов AnyService и AnyRepository, обращаясь к ним.
 */
@WebServlet(name = "AnyServlet", urlPatterns = "/")
public class AnyServlet extends HttpServlet {
    private AnyService service;

    @Override
    public void init() throws ServletException {
        ApplicationContext ac = (ApplicationContext) getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        service = ac.getBean(AnyService.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().println(service != null ? service.getServiceMessage() : "service is null");
    }
}
