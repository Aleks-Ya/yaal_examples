package handler.filter;

import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.junit.Test;

import static util.NetAsserts.assertUrlContent;

public class FilterHandlerTest {

    @Test
    public void authFilter() throws Exception {
        var showAuthHandler = new ShowAuthHandler();
        var authFilter = new AuthFilterHandler();
        authFilter.setHandler(showAuthHandler);

        var server = new Server(0);
        server.setHandler(authFilter);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port, "<h1>Auth: authorized</h1>");

        server.stop();
    }
}