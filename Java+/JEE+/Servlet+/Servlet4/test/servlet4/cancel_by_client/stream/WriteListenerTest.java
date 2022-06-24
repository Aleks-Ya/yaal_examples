package servlet4.cancel_by_client.stream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Servlet understands when client close connection.
 */
class WriteListenerTest {

    @Test
    void test() throws Exception {
        var handler = new ServletHandler();
        var servletPath = "/servlet";
        handler.addServletWithMapping(WriteListenerServlet.class, servletPath);

        var port = 8089;
        var server = new Server(port);
        server.setHandler(handler);
        server.start();

        var url = new URL("http://localhost:" + port + servletPath);
        var conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        conn.getInputStream().close();
        conn.disconnect();

        await()
                .atMost(5, SECONDS)
                .pollInterval(100, MILLISECONDS)
                .until(() -> WriteListenerServlet.error != null);

        assertThat(WriteListenerServlet.error).isEqualTo(new RuntimeException());
        assertThat(WriteListenerServlet.wroteText).hasToString("0 1 ");

        server.stop();
    }
}