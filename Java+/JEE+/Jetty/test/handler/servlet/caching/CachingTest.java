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
import org.junit.Test;
import util.NetAsserts;

import java.io.File;
import java.net.URL;

public class CachingTest {

    @Test
    public void test() throws Exception {
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(RootPageServlet.class, "/cache/root");
        servletHandler.addServletWithMapping(CachedPageServlet.class, "/cache/data");

        ResourceHandler resourceHandler = new ResourceHandler();
        URL res = CachingTest.class.getResource("fake.js");
        File staticContentDir = new File(res.getFile()).getParentFile();
        resourceHandler.setResourceBase(staticContentDir.getAbsolutePath());
        resourceHandler.setCacheControl("public, max-age=3600");
        resourceHandler.setEtags(true);
        MimeTypes mimeTypes = new MimeTypes();
        mimeTypes.addMimeMapping("js", "application/javascript");
        resourceHandler.setMimeTypes(mimeTypes);

        ServletContextHandler rootContext = new ServletContextHandler();
        rootContext.setServletHandler(servletHandler);

        ContextHandler staticContext = new ContextHandler("/static");
        staticContext.setHandler(resourceHandler);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, staticContext});

        Server server = new Server(0);
        server.setHandler(contexts);
        server.start();
        int port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        NetAsserts.assertUrlContent("http://localhost:" + port + "/static/fake.js", "console.log('hi from static JS');");
        NetAsserts.assertUrlContent("http://localhost:" + port + "/cache/root", RootPageServlet.CONTENT);
        NetAsserts.assertUrlContent("http://localhost:" + port + "/cache/data", CachedPageServlet.CONTENT);

        server.stop();
    }
}