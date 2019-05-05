package handler.resource.static_content;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.Test;
import util.NetAsserts;

import java.io.File;
import java.net.URL;

/**
 * Jetty provides static content from disk.
 */
public class StaticContentTest {

    /**
     * Resources are stored out of classpath.
     */
    @Test
    public void specialResourceDirectory() throws Exception {
        ResourceHandler handler = new ResourceHandler();
        String staticContentPath = new File("static_content").getAbsolutePath();
        handler.setResourceBase(staticContentPath);
        handler.setDirectoriesListed(true);

        Server server = new Server(0);
        server.setHandler(handler);
        server.start();
        int port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        NetAsserts.assertUrlContent("http://localhost:" + port, "Hi, HTML!");
        NetAsserts.assertUrlContent("http://localhost:" + port + "/nested/info.json", "{\"a\": 1}");

        server.stop();
    }

    /**
     * Resources are stored in classpath.
     */
    @Test
    public void resourcesInClasspath() throws Exception {
        ResourceHandler handler = new ResourceHandler();
        URL res = StaticContentTest.class.getResource("index.html");
        File staticContentDir = new File(res.getFile()).getParentFile();
        handler.setResourceBase(staticContentDir.getAbsolutePath());

        Server server = new Server(0);
        server.setHandler(handler);
        server.start();
        int port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        NetAsserts.assertUrlContent("http://localhost:" + port, "Hi, Static content from resources!");
        NetAsserts.assertUrlContent("http://localhost:" + port + "/nested/data.json", "{\"name\": \"John\"}");

        server.stop();
    }

    /**
     * Resources are stored in classpath.
     */
    @Test
    public void customResourceRoot() throws Exception {
        ResourceHandler handler = new ResourceHandler();
        URL res = StaticContentTest.class.getResource("index.html");
        File staticContentDir = new File(res.getFile()).getParentFile();
        handler.setResourceBase(staticContentDir.getAbsolutePath());

        ContextHandler staticContext = new ContextHandler("/static");
        staticContext.setHandler(handler);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{staticContext});

        Server server = new Server(0);
        server.setHandler(contexts);
        server.start();
        int port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        NetAsserts.assertUrlContent("http://localhost:" + port + "/static/nested/data.json", "{\"name\": \"John\"}");

        server.stop();
    }

    /**
     * Custom welcome file.
     */
    @Test
    public void customWelcomeFile() throws Exception {
        ResourceHandler handler = new ResourceHandler();
        URL res = StaticContentTest.class.getResource("index.html");
        File staticContentDir = new File(res.getFile()).getParentFile();
        handler.setResourceBase(staticContentDir.getAbsolutePath());
        handler.setWelcomeFiles(new String[]{"index2.html"});

        Server server = new Server(0);
        server.setHandler(handler);
        server.start();
        int port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        NetAsserts.assertUrlContent("http://localhost:" + port, "Hi, custom welcome file!");

        server.stop();
    }
}