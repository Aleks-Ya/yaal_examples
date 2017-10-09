package error;

import error.servlet.ChangeStatusServlet;
import error.servlet.ErrorServlet;
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
        ServletContextHandler errorContext = new ServletContextHandler(ServletContextHandler.SESSIONS);

        ServletHolder errorHolder = new ServletHolder(ErrorServlet.class);
        ServletHolder changeStatusHolder = new ServletHolder(ChangeStatusServlet.class);
        errorContext.addServlet(errorHolder, "/error");
        errorContext.addServlet(changeStatusHolder, "/status");

        ContextHandlerCollection contextHandlers = new ContextHandlerCollection();
        contextHandlers.setHandlers(new Handler[]{errorContext});

        Server server = new Server(8089);
        server.setHandler(contextHandlers);
        server.start();
        server.join();
    }
}