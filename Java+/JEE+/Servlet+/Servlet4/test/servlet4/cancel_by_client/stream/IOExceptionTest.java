package servlet4.cancel_by_client.stream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Servlet understands when client close connection.
 */
public class IOExceptionTest {

    @Test
    public void test() throws Exception {
        ServletHandler handler = new ServletHandler();
        String servletPath = "/servlet";
        handler.addServletWithMapping(IOExceptionServlet.class, servletPath);

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
                .atMost(3, SECONDS)
                .pollInterval(100, MILLISECONDS)
                .untilAsserted(() -> assertTrue(IOExceptionServlet.error != null));

        assertThat(IOExceptionServlet.error, Matchers.instanceOf(IOException.class));
        assertThat(IOExceptionServlet.wroteText.toString(), equalTo("0 1 "));

        server.stop();
    }
}