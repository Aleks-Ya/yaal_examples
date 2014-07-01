import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * http://localhost:8080/ -> "Hello World!"
 * http://localhost:8080/welcome -> "Welcome!"
 */
public class Main {
    public static void main(String[] args) throws Exception {
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(HelloWorldServlet.class, "/*");
        handler.addServletWithMapping(WelcomeServlet.class, "/welcome");

        Server server = new Server(8080);
        server.setHandler(handler);
        server.start();
        server.join();
    }
}