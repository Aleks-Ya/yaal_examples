package handler.servlet;

import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Test;
import util.NetAsserts;

import static util.NetAsserts.assertUrlContent;

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
        var handler = new ServletHandler();
        handler.addServletWithMapping(StarServlet.class, "/*");
        handler.addServletWithMapping(WelcomeServlet.class, "/welcome");

        var server = new Server(0);
        server.setHandler(handler);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port + "/*", "Star servlet");
        assertUrlContent("http://localhost:" + port + "/welcome", "<h2>Welcome!</h2>");

        server.stop();
    }
}