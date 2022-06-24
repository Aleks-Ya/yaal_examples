package servlet4.cancel_by_client.stream;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

/**
 * Servlet understands when client close connection.
 */
class IOExceptionTest {

    @Test
    void test() throws Exception {
        var handler = new ServletHandler();
        var servletPath = "/servlet";
        handler.addServletWithMapping(IOExceptionServlet.class, servletPath);

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
                .atMost(3, SECONDS)
                .pollInterval(100, MILLISECONDS)
                .untilAsserted(() -> assertThat(IOExceptionServlet.error).isNotNull());

        assertThat(IOExceptionServlet.error).isInstanceOf(IOException.class);
        assertThat(IOExceptionServlet.wroteText).hasToString("0 1 ");

        server.stop();
    }
}