package handler.context.async;

import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.Test;
import util.NetAsserts;

/**
 * Asynchronous servlet execution.
 */
public class AsyncServletTest {

    @Test
    public void test() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        String path = "/async";
        ServletHolder asyncHolder = context.addServlet(AsyncServlet.class, path);
        asyncHolder.setAsyncSupported(true);

        Server server = new Server(0);
        server.setHandler(context);
        server.start();
        int port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        NetAsserts.assertUrlContent("http://localhost:" + port + path, "Async respond body");

        server.stop();
    }
}