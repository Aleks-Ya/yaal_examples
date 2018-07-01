package net.socket;

import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ServerSocketTest {
    private static final String BODY = "abc";
    private static final int PORT = 2512;
    private boolean ready = false;

    @Test
    @Ignore("not finished")
    public void main() throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                ready = true;
                Socket socket = serverSocket.accept();
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.write(BODY);
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }).start();

        while (!ready) {
            Thread.sleep(100);
        }

        URL url = new URL("http", "localhost", PORT, "");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        BufferedReader bis = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        String act = bis.lines().collect(Collectors.joining("\n"));
        connection.disconnect();

        assertThat(act, equalTo(BODY));
    }
}
