package static_content;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.Test;
import util.Utils;

import java.io.File;

import static org.junit.Assert.assertThat;

/**
 * Jetty provides static content from disk.
 */
public class StaticContentTest {

    @Test
    public void main() throws Exception {
        ResourceHandler handler = new ResourceHandler();
        String staticContentPath = new File("static_content").getAbsolutePath();
        handler.setResourceBase(staticContentPath);
        handler.setDirectoriesListed(true);

        int port = 8080;
        Server server = new Server(port);
        server.setHandler(handler);
        server.start();

        Utils.assertUrlContent("http://localhost:" + port, "Hi, HTML!");
        Utils.assertUrlContent("http://localhost:" + port + "/nested/info.json", "{\"a\": 1}");

        server.stop();
    }
}