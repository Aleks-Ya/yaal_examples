package error;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Open: http://localhost:8089/error
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ServletHolder holder = new ServletHolder(ErrorServlet.class);
        ServletContextHandler uploadContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        uploadContext.addServlet(holder, "/error");

        ContextHandlerCollection contextHandlers = new ContextHandlerCollection();
        contextHandlers.setHandlers(new Handler[]{uploadContext});

        Server server = new Server(8089);
        server.setHandler(contextHandlers);
        server.start();
        server.join();
    }
}