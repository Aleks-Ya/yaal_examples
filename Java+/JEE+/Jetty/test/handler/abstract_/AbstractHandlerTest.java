package handler.abstract_;

import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import util.NetAsserts;

/**
 * See HelloWorld on http://localhost:8080/
 */
public class AbstractHandlerTest {
    public static void main(String[] args) throws Exception {
        Server server = new Server(0);
        server.setHandler(new HelloWorldHandler());
        server.start();
        int port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        NetAsserts.assertUrlContent("http://localhost:" + port, "<h1>Hello Jetty World!</h1>");

        server.stop();
    }
}