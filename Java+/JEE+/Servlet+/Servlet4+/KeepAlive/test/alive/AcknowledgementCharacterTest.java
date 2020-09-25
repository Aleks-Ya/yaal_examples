package alive;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AcknowledgementCharacterTest {

    @Test
    public void test() throws Exception {
        ServletHandler handler = new ServletHandler();
        String servletPath = "/servlet";
        handler.addServletWithMapping(AcknowledgementCharacterServlet.class, servletPath);

        int port = 8089;
        Server server = new Server(port);
        server.setHandler(handler);
        server.setStopTimeout(5);
        server.start();

        URL url = new URL("http://localhost:" + port + servletPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        InputStream is = conn.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        int b;
        StringBuilder input = new StringBuilder();
        while ((b = bis.read()) != -1) {
            input.append(b);
        }
        conn.disconnect();

//        assertThat(AcknowledgementCharacterServlet.writeCount, equalTo(2L));
        assertThat(input, equalTo(""));

        server.stop();
    }
}