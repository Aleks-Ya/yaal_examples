package cancel.stream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Servlet understands when client close connection.
 */
public class WriteListenerTest {

    @Test
    public void test() throws Exception {
        ServletHandler handler = new ServletHandler();
        String servletPath = "/servlet";
        handler.addServletWithMapping(WriteListenerServlet.class, servletPath);

        int port = 8089;
        Server server = new Server(port);
        server.setHandler(handler);
        server.start();

        URL url = new URL("http://localhost:" + port + servletPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        conn.getInputStream().close();
        conn.disconnect();

        await()
                .atMost(5, SECONDS)
                .pollInterval(100, MILLISECONDS)
                .untilAsserted(() -> assertTrue(WriteListenerServlet.error != null));

        assertThat(WriteListenerServlet.error, equalTo(new RuntimeException()));
        assertThat(WriteListenerServlet.wroteText.toString(), equalTo("0 1 "));

        server.stop();
    }
}