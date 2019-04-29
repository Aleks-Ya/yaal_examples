package net.socket;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class ServerSocketTest {
    private static final String BODY = "abc";
    private static final int PORT = 2512;
    private boolean ready = false;

    @Test
    public void main() throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(PORT);
                ready = true;
                Socket socket = serverSocket.accept();
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.write(BODY);
                pw.flush();
                socket.close();
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }).start();

        while (!ready) {
            Thread.sleep(500);
        }


        Socket socket = new Socket("127.0.0.1", PORT);
        BufferedReader bis = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        String act = bis.lines().collect(Collectors.joining("\n"));
        socket.close();

        assertThat(act, equalTo(BODY));
    }
}
