package handler.resource.static_content;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.jupiter.api.Test;

import java.io.File;

import static util.NetAsserts.assertUrlContent;

/**
 * Jetty provides static content from disk.
 */
public class StaticContentTest {

    /**
     * Resources are stored out of classpath.
     */
    @Test
    void specialResourceDirectory() throws Exception {
        var handler = new ResourceHandler();
        var staticContentPath = new File("static_content").getAbsolutePath();
        handler.setResourceBase(staticContentPath);
        handler.setDirectoriesListed(true);

        var server = new Server(0);
        server.setHandler(handler);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port, "Hi, HTML!");
        assertUrlContent("http://localhost:" + port + "/nested/info.json", "{\"a\": 1}");

        server.stop();
    }

    /**
     * Resources are stored in classpath.
     */
    @Test
    void resourcesInClasspath() throws Exception {
        var handler = new ResourceHandler();
        var res = StaticContentTest.class.getResource("index.html");
        var staticContentDir = new File(res.getFile()).getParentFile();
        handler.setResourceBase(staticContentDir.getAbsolutePath());

        var server = new Server(0);
        server.setHandler(handler);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port, "Hi, Static content from resources!");
        assertUrlContent("http://localhost:" + port + "/nested/data.json", "{\"name\": \"John\"}");

        server.stop();
    }

    /**
     * Resources are stored in classpath.
     */
    @Test
    void customResourceRoot() throws Exception {
        var handler = new ResourceHandler();
        var res = StaticContentTest.class.getResource("index.html");
        var staticContentDir = new File(res.getFile()).getParentFile();
        handler.setResourceBase(staticContentDir.getAbsolutePath());

        var staticContext = new ContextHandler("/static");
        staticContext.setHandler(handler);

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{staticContext});

        var server = new Server(0);
        server.setHandler(contexts);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port + "/static/nested/data.json", "{\"name\": \"John\"}");

        server.stop();
    }

    /**
     * Custom welcome file.
     */
    @Test
    void customWelcomeFile() throws Exception {
        var handler = new ResourceHandler();
        var res = StaticContentTest.class.getResource("index.html");
        var staticContentDir = new File(res.getFile()).getParentFile();
        handler.setResourceBase(staticContentDir.getAbsolutePath());
        handler.setWelcomeFiles(new String[]{"index2.html"});

        var server = new Server(0);
        server.setHandler(handler);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port, "Hi, custom welcome file!");

        server.stop();
    }
}