package handler.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static utilt.NetAsserts.assertUrlContent;

/**
 *
 */
public class ErrorHandlerTest {

    @Test
    @Disabled("does not work")
    public void test() throws Exception {
        var handler = new ServletHandler();
        var path = "/error";
        handler.addServletWithMapping(ErrorServlet.class, path);

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

        var server = new Server(0);
        server.setHandler(handler);
        server.setErrorHandler(errorHandler);
        server.addBean(errorHandler);
        server.start();
        var port = ((NetworkConnector) server.getConnectors()[0]).getLocalPort();

        assertUrlContent("http://localhost:" + port + path, "Star servlet");

        server.stop();
    }
}