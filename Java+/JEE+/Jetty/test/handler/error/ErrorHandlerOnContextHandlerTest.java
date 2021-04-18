package handler.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.stream.Collectors;

/**
 *
 */
public class ErrorHandlerOnContextHandlerTest {

    @Test
    @Disabled("does not work")
    public void test() throws Exception {
        var servletHandler = new ServletHandler();
        var path = "/error";
        servletHandler.addServletWithMapping(ErrorOnContextHandlerServlet.class, path);

        var errorHandler = new ErrorHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
                System.out.println("Handle error");
            }

            @Override
            public ByteBuffer badMessageError(int status, String reason, HttpFields.Mutable fields) {
                System.out.println("Handle bad message");
                return ByteBuffer.wrap(new byte[0]);
            }
        };

        var rootContext = new ContextHandler();
        rootContext.setHandler(servletHandler);
        rootContext.setErrorHandler(errorHandler);

        var server = new Server(0);
        server.setHandler(servletHandler);
        server.setErrorHandler(errorHandler);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

//        runClient("http://localhost:" + port + path);
        runClient1bytes("http://localhost:" + port + path);
//        NetAsserts.assertUrlContent("http://localhost:" + port + path, "Star servlet");

        server.stop();
    }

    private void runClient(String url) throws IOException {
        var conn = (HttpURLConnection) new URL(url).openConnection();
        conn.connect();
        var responseCode = conn.getResponseCode();
        System.out.println("Response code: " + responseCode);
        var contentLength = conn.getContentLength();
//        assertThat(contentLength, greaterThan(0));
        var bis = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        var content = bis.lines().collect(Collectors.joining("\n"));
        System.out.println("Content: " + content);
        conn.disconnect();
    }

    private void runClient1bytes(String url) throws IOException {
        var conn = (HttpURLConnection) new URL(url).openConnection();
        conn.connect();
//        int responseCode = conn.getResponseCode();
//        System.out.println("Response code: " + responseCode);
//        int contentLength = conn.getContentLength();
//        assertThat(contentLength, greaterThan(0));
        var b = conn.getInputStream().read();
        System.out.println("Byte: " + (char) b);
        conn.disconnect();
    }
}