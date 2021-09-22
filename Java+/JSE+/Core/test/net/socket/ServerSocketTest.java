package net.socket;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Run ServerSocket and send a request from Client Socket.
 */
class ServerSocketTest {
    private static final String BODY = "abc";
    private static final int PORT = 2517;

    @Test
    void test() throws IOException, ExecutionException, InterruptedException {
        var serverSocket = new ServerSocket(PORT);
        Future<Void> serverFuture = Executors.newSingleThreadExecutor().submit(() -> {
            var socket = serverSocket.accept();
            var pw = new PrintWriter(socket.getOutputStream());
            pw.write(BODY);
            pw.flush();
            socket.close();
            serverSocket.close();
            return null;
        });

        var socket = new Socket("127.0.0.1", PORT);
        var bis = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        var act = bis.lines().collect(Collectors.joining("\n"));
        socket.close();
        assertThat(act, equalTo(BODY));

        serverFuture.get();
    }
}
