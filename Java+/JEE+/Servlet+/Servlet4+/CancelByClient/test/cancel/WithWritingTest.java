package cancel;

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
 * (Servlet is instantly writing to response's PrintWriter.
 */
public class WithWritingTest {

    @Test
    public void test() throws Exception {
        ServletHandler handler = new ServletHandler();
        String servletPath = "/servlet";
        handler.addServletWithMapping(WithWritingServlet.class, servletPath);

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
                .untilAsserted(() -> assertTrue(WithWritingServlet.checkError));

        assertThat(WithWritingServlet.writeCount, equalTo(2L));
        assertThat(WithWritingServlet.wroteText.toString(), equalTo("0 1 "));

        server.stop();
    }
}