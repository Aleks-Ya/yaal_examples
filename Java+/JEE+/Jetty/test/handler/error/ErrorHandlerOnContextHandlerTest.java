package handler.error;

import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.Ignore;
import org.junit.Test;
import util.NetAsserts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
public class ErrorHandlerOnContextHandlerTest {

    @Test
    @Ignore("does not work")
    public void test() throws Exception {
        ServletHandler servletHandler = new ServletHandler();
        String path = "/error";
        servletHandler.addServletWithMapping(ErrorOnContextHandlerServlet.class, path);


        ErrorHandler errorHandler = new ErrorHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
                System.out.println("Handle error");
            }

            @Override
            public ByteBuffer badMessageError(int status, String reason, HttpFields fields) {
                System.out.println("Handle bad message");
                return ByteBuffer.wrap(new byte[0]);
            }
        };

        ContextHandler rootContext = new ContextHandler();
        rootContext.setHandler(servletHandler);
        rootContext.setErrorHandler(errorHandler);

        Server server = new Server(0);
        server.setHandler(servletHandler);
        server.setErrorHandler(errorHandler);
        server.start();
        int port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

//        runClient("http://localhost:" + port + path);
        runClient1bytes("http://localhost:" + port + path);
//        NetAsserts.assertUrlContent("http://localhost:" + port + path, "Star servlet");

        server.stop();
    }

    private void runClient(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.connect();
        int responseCode = conn.getResponseCode();
        System.out.println("Response code: " + responseCode);
        int contentLength = conn.getContentLength();
//        assertThat(contentLength, greaterThan(0));
        BufferedReader bis = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String content = bis.lines().collect(Collectors.joining("\n"));
        System.out.println("Content: " + content);
        conn.disconnect();
    }

    private void runClient1bytes(String url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.connect();
//        int responseCode = conn.getResponseCode();
//        System.out.println("Response code: " + responseCode);
//        int contentLength = conn.getContentLength();
//        assertThat(contentLength, greaterThan(0));
        int b = conn.getInputStream().read();
        System.out.println("Byte: " + (char) b);
        conn.disconnect();
    }
}