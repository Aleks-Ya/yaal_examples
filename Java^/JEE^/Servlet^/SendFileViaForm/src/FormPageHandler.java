import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Страница с формой для отправки файла в сервлет {@linkplain HelloWorldServlet}.
 */
class FormPageHandler extends AbstractHandler {

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        response.getWriter().printf("<html><body>");
        response.getWriter().printf("<form action='/upload' method='POST'>");
        response.getWriter().printf("<input type='file' name='f'/>");
        response.getWriter().printf("<br/>");
        response.getWriter().printf("<input type='submit' value='submit'/>");
        response.getWriter().printf("</form>");
        response.getWriter().printf("</body></html>");
    }
}