package error;

import error.servlet.SendDataForeverServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Open: http://localhost:8089/forever
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ServletContextHandler errorContext = new ServletContextHandler(ServletContextHandler.SESSIONS);

        ServletHolder errorHolder = new ServletHolder(SendDataForeverServlet.class);
        errorContext.addServlet(errorHolder, "/forever");

        ContextHandlerCollection contextHandlers = new ContextHandlerCollection();
        contextHandlers.setHandlers(new Handler[]{errorContext});

        Server server = new Server(8089);
        server.setHandler(contextHandlers);
        server.start();
        server.join();
    }
}