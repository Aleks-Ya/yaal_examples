import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Test;
import util.Utils;

/**
 * Использование сервлетов в Jetty Embedded.
 * Взят из документации http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html
 * <p>
 * http://localhost:8080/ -> "Hello World!"
 * http://localhost:8080/welcome -> "Welcome!"
 */
public class ServletHandlerTest {

    @Test
    public void main() throws Exception {
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(StarServlet.class, "/*");
        handler.addServletWithMapping(WelcomeServlet.class, "/welcome");

        int port = 8080;
        Server server = new Server(port);
        server.setHandler(handler);
        server.start();

        Utils.assertUrlContent("http://localhost:" + port + "/*", "Star servlet");
        Utils.assertUrlContent("http://localhost:" + port + "/welcome", "<h2>Welcome!</h2>");

        server.stop();
    }
}