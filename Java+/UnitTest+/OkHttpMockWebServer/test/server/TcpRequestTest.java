package server;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static okhttp3.mockwebserver.SocketPolicy.DISCONNECT_AT_END;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Client uses {@link Socket} for sending HTTP request to {@link MockWebServer}.
 */
class TcpRequestTest {

    @Test
    void socket() throws IOException, InterruptedException {
        try (var server = new MockWebServer()) {
            var body1 = "hello, world!";
            var body2 = "buy all!";
            server.enqueue(new MockResponse().setBody(body1).setSocketPolicy(DISCONNECT_AT_END));
            server.enqueue(new MockResponse().setBody(body2).setSocketPolicy(DISCONNECT_AT_END));

            server.start();

            var host = server.getHostName();
            var port = server.getPort();

            var actResponse1 = readSocket(host, port);
            var expResponse1 = expResponse(body1);
            assertThat(actResponse1).isEqualTo(expResponse1);

            var actResponse2 = readSocket(host, port);
            var expResponse2 = expResponse(body2);
            assertThat(actResponse2).isEqualTo(expResponse2);

            var request1 = server.takeRequest();
            assertThat(request1.getPath()).isEqualTo("/");
            assertThat(request1.getMethod()).isEqualTo("GET");

            var request2 = server.takeRequest();
            assertThat(request2.getPath()).isEqualTo("/");
            assertThat(request2.getMethod()).isEqualTo("GET");
        }
    }

    private String readSocket(String host, int port) throws IOException {
        try (var socket = new Socket(host, port)) {
            var pw = new PrintWriter(socket.getOutputStream());
            pw.println("GET / HTTP/1.1");
            pw.println("Host: " + host);
            pw.println("");
            pw.flush();

            var br = new BufferedReader(new InputStreamReader((socket.getInputStream())));
            String line;
            var sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }

    private String expResponse(String body) {
        return String.format("HTTP/1.1 200 OK\nContent-Length: %d\n\n%s\n", body.length(), body);
    }
}
