package httpserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * Использование HTTP-сервера из JDK (пакет com.sun.net.httpserver).
 */
public class Server {
    public static void main(String[] args) throws IOException {
        int maxBacklog = 1;
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), maxBacklog);
        server.createContext("/applications/myapp", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            request(t);
            response(t);
        }

        private void request(HttpExchange t) throws IOException {
            InputStream is = t.getRequestBody();
            Headers headers = t.getRequestHeaders();
            System.out.println("Request headers:\n" + headers.entrySet());
            System.out.println("Request body:\n" + is.read(new byte[is.available()]));
        }

        private void response(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
