package servlet4.listener.write;

import org.eclipse.jetty.io.EofException;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

class WriteListenerTest {
    @Test
    void test() throws Exception {
        var context = new ServletContextHandler();
        context.setContextPath("/");
        var path = "/servlet";
        var asyncHolder = context.addServlet(WriteListenerServlet.class, path);
        asyncHolder.setAsyncSupported(true);

        var server = new Server(0);
        server.setHandler(context);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        var url = new URL("http://localhost:" + port + path);
        var conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        conn.getInputStream().close();
        conn.disconnect();

        await()
                .atMost(5, SECONDS)
                .pollInterval(100, MILLISECONDS)
                .until(() -> WriteListenerServlet.onErrorThrowable != null);

        assertThat(WriteListenerServlet.onWritePossibleInvoked).isTrue();

        var e = WriteListenerServlet.onErrorThrowable;
        assertThat(e).isInstanceOf(EofException.class).message().isNull();

        var cause = e.getCause();
        assertThat(cause).isInstanceOf(IOException.class).hasMessage("Broken pipe");

        server.stop();
    }
}