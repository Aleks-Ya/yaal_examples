package handler.abstract_;

import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;

import static util.NetAsserts.assertUrlContent;

/**
 * See HelloWorld on http://localhost:8080/
 */
public class AbstractHandlerTest {
    public static void main(String[] args) throws Exception {
        var server = new Server(0);
        server.setHandler(new HelloWorldHandler());
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port, "<h1>Hello Jetty World!</h1>");

        server.stop();
    }
}