package handler.servlet.caching;

import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.jupiter.api.Test;

import java.io.File;

import static util.NetAsserts.assertUrlContent;

public class CachingTest {

    @Test
    void test() throws Exception {
        var servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(RootPageServlet.class, "/cache/root");
        servletHandler.addServletWithMapping(CachedPageServlet.class, "/cache/data");

        var resourceHandler = new ResourceHandler();
        var res = CachingTest.class.getResource("fake.js");
        var staticContentDir = new File(res.getFile()).getParentFile();
        resourceHandler.setResourceBase(staticContentDir.getAbsolutePath());
        resourceHandler.setCacheControl("public, max-age=3600");
        resourceHandler.setEtags(true);
        var mimeTypes = new MimeTypes();
        mimeTypes.addMimeMapping("js", "application/javascript");
        resourceHandler.setMimeTypes(mimeTypes);

        var rootContext = new ServletContextHandler();
        rootContext.setServletHandler(servletHandler);

        var staticContext = new ContextHandler("/static");
        staticContext.setHandler(resourceHandler);

        var contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, staticContext});

        var server = new Server(0);
        server.setHandler(contexts);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port + "/static/fake.js", "console.log('hi from static JS');");
        assertUrlContent("http://localhost:" + port + "/cache/root", RootPageServlet.CONTENT);
        assertUrlContent("http://localhost:" + port + "/cache/data", CachedPageServlet.CONTENT);

        server.stop();
    }
}