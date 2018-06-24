package caching;

import org.eclipse.jetty.server.Handler;
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

        ServletContextHandler rootContext = new ServletContextHandler();
        rootContext.setServletHandler(servletHandler);

        ContextHandler staticContext = new ContextHandler("/static");
        staticContext.setHandler(resourceHandler);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.setHandlers(new Handler[]{rootContext, staticContext});

        int port = 8081;
        Server server = new Server(port);
        server.setHandler(contexts);
        server.start();

        NetAsserts.assertUrlContent("http://localhost:" + port + "/static/fake.js", "console.log('hi');");
        NetAsserts.assertUrlContent("http://localhost:" + port + "/cache/root", "RootPageServlet");

//        while(true);
        server.stop();
    }
}