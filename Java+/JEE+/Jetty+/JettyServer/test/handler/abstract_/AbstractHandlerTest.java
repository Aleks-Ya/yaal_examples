package handler.abstract_;

import org.eclipse.jetty.server.Server;
import util.Utils;

/**
 * See HelloWorld on http://localhost:8080/
 */
public class AbstractHandlerTest {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        Server server = new Server(port);
        server.setHandler(new HelloWorldHandler());
        server.start();

        Utils.assertUrlContent("http://localhost:" + port, "<h1>Hello Jetty World!</h1>");

        server.stop();
    }
}