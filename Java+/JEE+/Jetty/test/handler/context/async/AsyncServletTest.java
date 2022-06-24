package handler.context.async;

import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.jupiter.api.Test;

import static util.NetAsserts.assertUrlContent;

/**
 * Asynchronous servlet execution.
 */
public class AsyncServletTest {

    @Test
    void test() throws Exception {
        var context = new ServletContextHandler();
        var path = "/async";
        var asyncHolder = context.addServlet(AsyncServlet.class, path);
        asyncHolder.setAsyncSupported(true);

        var server = new Server(0);
        server.setHandler(context);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port + path, "Async respond body");

        server.stop();
    }
}