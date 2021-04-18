package servlet4.listener.write;

import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

public class WriteListenerTest {

    @Test
    public void test() throws Exception {
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        String path = "/servlet";
        ServletHolder asyncHolder = context.addServlet(WriteListenerServlet.class, path);
        asyncHolder.setAsyncSupported(true);

        Server server = new Server(0);
        server.setHandler(context);
        server.start();
        int port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        URL url = new URL("http://localhost:" + port + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        conn.getInputStream().close();
        conn.disconnect();

        await()
                .atMost(5, SECONDS)
                .pollInterval(100, MILLISECONDS)
                .untilAsserted(() -> assertNotNull(WriteListenerServlet.onErrorThrowable));

        assertTrue(WriteListenerServlet.onWritePossibleInvoked);

        Throwable e = WriteListenerServlet.onErrorThrowable;
        assertThat(e, instanceOf(EofException.class));
        assertThat(e.getMessage(), nullValue());

        Throwable cause = e.getCause();
        assertThat(cause, instanceOf(IOException.class));
        assertThat(cause.getMessage(), equalTo("Broken pipe"));

        server.stop();
    }
}