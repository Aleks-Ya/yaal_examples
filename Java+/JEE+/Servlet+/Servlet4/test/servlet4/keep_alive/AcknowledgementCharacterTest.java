package servlet4.keep_alive;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class AcknowledgementCharacterTest {

    @Test
    void test() throws Exception {
        var handler = new ServletHandler();
        var servletPath = "/servlet";
        handler.addServletWithMapping(AcknowledgementCharacterServlet.class, servletPath);

        var port = 8089;
        var server = new Server(port);
        server.setHandler(handler);
        server.setStopTimeout(5);
        server.start();

        var url = new URL("http://localhost:" + port + servletPath);
        var conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        var is = conn.getInputStream();
        var bis = new BufferedInputStream(is);
        int b;
        var input = new StringBuilder();
        while ((b = bis.read()) != -1) {
            input.append(b);
        }
        conn.disconnect();

//        assertThat(AcknowledgementCharacterServlet.writeCount, equalTo(2L));
        assertThat(input).hasToString("");

        server.stop();
    }
}